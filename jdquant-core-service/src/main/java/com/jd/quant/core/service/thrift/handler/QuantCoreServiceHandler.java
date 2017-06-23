package com.jd.quant.core.service.thrift.handler;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jd.quant.core.common.support.QuantTaskConstants;
import com.jd.quant.core.common.utils.DateUtils;
import com.jd.quant.core.domain.common.QuantTaskRequest;
import com.jd.quant.core.domain.instrument.Instrument;
import com.jd.quant.core.domain.profit.*;
import com.jd.quant.core.domain.thrift.*;
import com.jd.quant.core.service.event.RegressionEndEvent;
import com.jd.quant.core.service.thrift.QuantCoreService;
import com.jd.quant.core.service.utils.QuantResultUtils;
import com.jd.quant.core.service.utils.ThriftDomainUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Thrift接口实现类
 *
 * @author Zhiguo.Chen
 */
@Component
public class QuantCoreServiceHandler implements QuantCoreService.Iface {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuantCoreServiceHandler.class);

    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    protected static final Integer MAX_DAYS_OF_MINUTE_RESULT = 10;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 策略执行异常反馈
     *
     * @param addExceptionRequest
     */
    @Override
    public TQuantCoreResponse addQuantException(TQuantAddExceptionRequest addExceptionRequest) throws TException {
        Assert.notNull(addExceptionRequest.getUserPin(), "userPin不可为null！");
        Assert.notNull(addExceptionRequest.getStrategyId(), "strategyId不可为null！");
        Assert.notNull(addExceptionRequest.getRequestTime(), "requestTime不可为null！");
        Assert.notNull(addExceptionRequest.getStrategyType(), "strategyType不可为null！");
        TQuantCoreResponse coreResponse = new TQuantCoreResponse();

        Long strategyId = addExceptionRequest.getStrategyId();
        String message = addExceptionRequest.getMessage();
        String userPin = addExceptionRequest.getUserPin();
        Long requestTime = addExceptionRequest.getRequestTime();
        Integer strategyType = addExceptionRequest.getStrategyType();

        try {
            //设置Redis中运行状态为发生异常
            QuantResultUtils.setRunType(userPin, strategyId, requestTime, strategyType, QuantTaskConstants.RUN_TYPE_EXCEPTION);
            QuantResultUtils.clearUUID(userPin, strategyId, strategyType);
            QuantResultUtils.putErrorResult(userPin, strategyId, requestTime, message, strategyType);

            coreResponse.setSuccess(true);
        } catch (Exception e) {
            LOGGER.error("操作Redis值失败:", e);
            coreResponse.setSuccess(false);
            coreResponse.setMessage("操作Redis值失败！");
        }
        return coreResponse;
    }

    /**
     * 保存策略运行结果（回测）参数为结果
     *
     * @param saveResultRequest
     */
    @Override
    public TQuantCoreResponse saveResult(TQuantSaveResultTaskRequest saveResultRequest) throws TException {
        TQuantCoreResponse response = new TQuantCoreResponse();
        QuantTaskRequest taskRequest = new QuantTaskRequest();
        taskRequest.setUserPin(saveResultRequest.getUserPin());
        taskRequest.setStrategyId(saveResultRequest.getStrategyId());
        taskRequest.setRequestTime(saveResultRequest.getRequestTime());
        taskRequest.setStrategyType(QuantTaskConstants.TASK_TYPE_REGRESSION);

        if (CollectionUtils.isEmpty(saveResultRequest.getDayProfits())) {
            LOGGER.error("===回测===本次请求无需要保存结果！userPin:{}，strategyId:{}", saveResultRequest.getUserPin(), saveResultRequest.getStrategyId());
            response.setSuccess(true);
            response.setCode(QuantTaskConstants.RUN_TYPE_RUNNING);
            response.setMessage("没有结果内容！");
            return response;
        }

        try {
            DayProfit dayProfit;
            for (TDayProfit tDayProfit : saveResultRequest.getDayProfits()) {
                if (null == tDayProfit) {
                    LOGGER.info("===回测===PythonDayProfit为null！userPin:{}，strategyId:{}", saveResultRequest.getUserPin(), saveResultRequest.getStrategyId());
                    continue;
                }

                dayProfit = gson.fromJson(gson.toJson(tDayProfit), DayProfit.class);
//            BeanUtils.copyProperties(tDayProfit, dayProfit);

                convertDayProfit(tDayProfit, dayProfit);

                response = saveDayProfit(saveResultRequest, dayProfit, saveResultRequest.isRunEnd());
            }
        } catch (Exception e) {
            LOGGER.error("===回测===Python保存运行结果异常，strategyId：{}", saveResultRequest.getStrategyId(), e);
            response.setSuccess(false);
            response.setCode(QuantTaskConstants.RUN_TYPE_EXCEPTION);
            response.setMessage("结果保存异常！");
            response.setDetailInfo(e.getMessage());
        }
        return response;
    }

    //对象转换
    private void convertDayProfit(TDayProfit tDayProfit, DayProfit dayProfit) {
        //持仓详情转换
        List<HoldDetail> details = Lists.newArrayListWithCapacity(tDayProfit.getHoldDetails().size());
        for (THoldDetail tHoldDetail : tDayProfit.getHoldDetails()) {
            HoldDetail detail = new HoldDetail();
//                    BeanUtils.copyProperties(tHoldDetail, detail);
            detail.setClosingPrice(new BigDecimal(tHoldDetail.getClosingPrice()));
            detail.setCostPrice(new BigDecimal(tHoldDetail.getCostPrice()));
            detail.setCount(new BigDecimal(tHoldDetail.getCount()));
            detail.setDayProfit(new BigDecimal(tHoldDetail.getDayProfit()));

            Instrument instrument = ThriftDomainUtils.toInstrument(tHoldDetail.getOrderBookId(), tDayProfit.getDate());
            detail.setInstrument(instrument);
            detail.setPositionPrice(new BigDecimal(tHoldDetail.getClosingPrice() * tHoldDetail.getCount()));
            details.add(detail);
        }
        dayProfit.setHoldDetails(details);

        //交易详情转换
        List<TransactionDetail> transactionDetails = Lists.newArrayListWithCapacity(tDayProfit.getTransactionDetails().size());
        for (TTransactionDetail transactionDetail : tDayProfit.getTransactionDetails()) {
            TransactionDetail detail = new TransactionDetail();
//                    BeanUtils.copyProperties(transactionDetail, detail);
            detail.setDate(DateUtils.string2Date(transactionDetail.getDate()));
            detail.setStockNumberFrom(transactionDetail.getStockNumberFrom());
            detail.setStockNumberTo(transactionDetail.getStockNumberTo());
            detail.setProportionFrom(new BigDecimal(transactionDetail.getProportionFrom()));
            detail.setProportionTo(new BigDecimal(transactionDetail.getProportionTo()));
            detail.setFlag(true);
            detail.setOrderType(transactionDetail.getOrderType());
            detail.setCount(new BigDecimal(transactionDetail.getCount()));
            detail.setPrice(new BigDecimal(transactionDetail.getPrice()));
            detail.setTotle(new BigDecimal(transactionDetail.getTotle()));
            detail.setCommission(new BigDecimal(transactionDetail.getCommission()));
            detail.setBuyCount(new BigDecimal(transactionDetail.getBuyCount()));
            detail.setTax(new BigDecimal(transactionDetail.getTax()));
            detail.setOrderStyle(transactionDetail.getOrderStyle());

            Instrument instrument = ThriftDomainUtils.toInstrument(transactionDetail.getOrderBookId(), tDayProfit.getDate());
            detail.setInstrument(instrument);
            transactionDetails.add(detail);
        }
        dayProfit.setTransactionDetailList(transactionDetails);

        //风险指标值转换
        QuantSummary quantSummary = dayProfit.getQuantSummary();
        if (null != quantSummary) {
//                BeanUtils.copyProperties(tDayProfit.getQuantSummary(), quantSummary);
            quantSummary.setCumulativePercent(new BigDecimal(tDayProfit.getCumulativePercent()));
            quantSummary.setBenchmarkPercent(new BigDecimal(tDayProfit.getBenchmarkPercent()));
            quantSummary.setYearPercent(new BigDecimal(tDayProfit.getYearPercent()));
            quantSummary.setYearBenchmarkPercent(new BigDecimal(tDayProfit.getYearBenchmarkPercent()));

            if (!CollectionUtils.isEmpty(tDayProfit.getRiskList())) {
                List<RiskResult> riskResults = gson.fromJson(gson.toJson(tDayProfit.getRiskList()), new TypeToken<List<RiskResult>>() {
                }.getType());
                quantSummary.setRiskList(riskResults);
            }
        }
    }

    //保存回测结果
    private TQuantCoreResponse saveDayProfit(TQuantSaveResultTaskRequest saveResultRequest, DayProfit dayProfit, boolean runEnd) {
        TQuantCoreResponse response = new TQuantCoreResponse();

        //将结果存储到缓内存中
        String result = QuantResultUtils.putRegressionResult(saveResultRequest.getUserPin(), saveResultRequest.getStrategyId(),
                saveResultRequest.getRequestTime(), saveResultRequest.getRunType(), dayProfit);

        if ("cancel".equalsIgnoreCase(result)) {
            LOGGER.info("===回测===用户已主动取消回测！strategyId:{}，userPin:{}", saveResultRequest.getStrategyId(), saveResultRequest.getUserPin());
            QuantResultUtils.clearRegressionResult(saveResultRequest.getUserPin(), saveResultRequest.getStrategyId(), saveResultRequest.getRequestTime(), saveResultRequest.getRunType());
            response.setCode(QuantTaskConstants.RUN_TYPE_CANCEL);
            response.setMessage("用户已取消回测");
            return response;
        }

        if (runEnd) { //回测是否结束
            //设置Redis中运行状态为结束
            QuantResultUtils.setRunType(saveResultRequest.getUserPin(), saveResultRequest.getStrategyId(), saveResultRequest.getRequestTime(), saveResultRequest.getRunType(), QuantTaskConstants.RUN_TYPE_END);

            QuantTaskRequest request = new QuantTaskRequest();
            request.setUserPin(saveResultRequest.getUserPin());
            request.setStrategyId(saveResultRequest.getStrategyId());
            request.setRequestTime(saveResultRequest.getRequestTime());
            request.setStrategyType(QuantTaskConstants.TASK_TYPE_REGRESSION);

            applicationContext.publishEvent(new RegressionEndEvent(request));

            response.setCode(QuantTaskConstants.RUN_TYPE_END);
        } else {
            //设置Redis中运行状态为正在运行
            QuantResultUtils.setRunType(saveResultRequest.getUserPin(), saveResultRequest.getStrategyId(), saveResultRequest.getRequestTime(), saveResultRequest.getRunType(), QuantTaskConstants.RUN_TYPE_RUNNING);
            response.setCode(QuantTaskConstants.RUN_TYPE_RUNNING);
        }

        return response;
    }


    /**
     * 保存策略运行结果（模拟）：参数为此刻持仓
     *
     * @param saveResultRequest
     */
    @Override
    public TQuantCoreResponse saveResultByInfoPacksForSimulation(TQuantSaveResultRequest saveResultRequest) throws TException {
        return null;
    }

    /**
     * 获取持仓信息
     *
     * @param taskRequest
     */
    @Override
    public TQuantGetInfoResponse getInfoPacks(TQuantTaskRequest taskRequest) throws TException {
        return null;
    }

    /**
     * 获取策略运行历史结果
     *
     * @param taskRequest
     */
    @Override
    public TQuantGetResultResponse getHistoryResult(TQuantTaskRequest taskRequest) throws TException {
        return null;
    }
}
