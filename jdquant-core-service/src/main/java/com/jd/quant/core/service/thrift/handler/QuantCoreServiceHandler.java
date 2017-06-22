package com.jd.quant.core.service.thrift.handler;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jd.quant.core.common.support.QuantTaskRunType;
import com.jd.quant.core.common.support.QuantTaskType;
import com.jd.quant.core.common.utils.DateUtils;
import com.jd.quant.core.domain.common.CommonResponse;
import com.jd.quant.core.domain.common.QuantTaskRequest;
import com.jd.quant.core.domain.instrument.Instrument;
import com.jd.quant.core.domain.position.InfoPacks;
import com.jd.quant.core.domain.profit.*;
import com.jd.quant.core.domain.thrift.*;
import com.jd.quant.core.service.thrift.QuantCoreService;
import com.jd.quant.core.service.utils.KeyUtil;
import com.jd.quant.core.service.utils.QuantResultUtils;
import com.jd.quant.core.service.utils.ThriftDomainUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Description:
 *
 * @author Zhiguo.Chen <me@chenzhiguo.cn>
 */
@Component
public class QuantCoreServiceHandler implements QuantCoreService.Iface {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuantCoreServiceHandler.class);

    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    protected static final Integer MAX_DAYS_OF_MINUTE_RESULT = 10;

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
//        Long strategyId = addExceptionRequest.getStrategyId();
//        String message = addExceptionRequest.getMessage();
//        String userPin = addExceptionRequest.getUserPin();
//        Long requestTime = addExceptionRequest.getRequestTime();

        try {
            //设置Redis中运行状态为发生异常
            QuantResultUtils.setRunType(addExceptionRequest.getUserPin(), addExceptionRequest.getStrategyId(),
                    addExceptionRequest.getRequestTime(), addExceptionRequest.getStrategyType(), QuantTaskRunType.EXCEPTION);
            QuantResultUtils.clearUUID(addExceptionRequest.getUserPin(), addExceptionRequest.getStrategyId(),
                    addExceptionRequest.getStrategyType());
            QuantResultUtils.putErrorResult(addExceptionRequest.getUserPin(), addExceptionRequest.getStrategyId(),
                    addExceptionRequest.getRequestTime(), addExceptionRequest.getMessage(), addExceptionRequest.getStrategyType());

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
        taskRequest.setStrategyType(QuantTaskType.REGRESSION.getCode());

        if (CollectionUtils.isEmpty(saveResultRequest.getDayProfits())) {
            LOGGER.error("===回测===本次请求无需要保存结果！userPin:{}，strategyId:{}", saveResultRequest.getUserPin(), saveResultRequest.getStrategyId());
            response.setSuccess(true);
            response.setCode(QuantTaskRunType.RUNNING.getState());
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
//            LOGGER.info("===回测===保存运行结果至Redis成功！StrategyId:{}，RequestTime:{}，StrategyType:{}，RunEnd:{}", taskRequest.getStrategyId(),
//                    taskRequest.getRequestTime(), taskRequest.getStrategyType(), saveResultRequest.isRunEnd());
        } catch (Exception e) {
            LOGGER.error("===回测===Python保存运行结果异常，strategyId：{}", saveResultRequest.getStrategyId(), e);
            response.setSuccess(false);
            response.setCode(QuantTaskRunType.EXCEPTION.getState());
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
            response.setCode(QuantTaskRunType.CANCEL.getState());
            response.setMessage("用户已取消回测");
            return response;
        }

        if (runEnd) { //回测是否结束
            //设置Redis中运行状态为结束
            QuantResultUtils.setRunType(saveResultRequest.getUserPin(), saveResultRequest.getStrategyId(), saveResultRequest.getRequestTime(), saveResultRequest.getRunType(), QuantTaskRunType.END);

            //执行end方法
            try {
                switch (saveResultRequest.getRunType()) {
                    case 1:
                        transactionSharesEndForRegression(saveResultRequest);
                        break;
//                    case 4:
//                        transactionSharesEndForContestRegression(saveResultRequest);
//                        break;
                    default:
                        throw new IllegalArgumentException("不支持的任务类型：" + saveResultRequest.getRunType());
                }
            } catch (Exception e) {
                LOGGER.error("===回测===调用transactionSharesEndForRegression异常：", e);
            }

            response.setCode(QuantTaskRunType.END.getState());
        } else {
            //设置Redis中运行状态为正在运行
            QuantResultUtils.setRunType(saveResultRequest.getUserPin(), saveResultRequest.getStrategyId(), saveResultRequest.getRequestTime(), saveResultRequest.getRunType(), QuantTaskRunType.RUNNING);
            response.setCode(QuantTaskRunType.RUNNING.getState());
        }

        return response;
    }

    //回测结束需执行的方法
    private CommonResponse transactionSharesEndForRegression(TQuantSaveResultTaskRequest taskRequest) throws Exception {
        QuantTaskRequest request = new QuantTaskRequest();
        request.setUserPin(taskRequest.getUserPin());
        request.setStrategyId(taskRequest.getStrategyId());
        request.setRequestTime(taskRequest.getRequestTime());
        request.setStrategyType(QuantTaskType.REGRESSION.getCode());
        CommonResponse response = CommonResponse.createCommonResponse();
//        try {
//            //获取对应的TransactionService：回测|实盘模拟|竞赛|实盘交易
//            try {
//                RegressionTransactionServiceImpl transactionService = (RegressionTransactionServiceImpl) transactionServiceFactory.getTransactionServiceImpl(QuantaTaskRequest.TASK_TYPE_REGRESSION);
//                //如果存在，则将结果文件存储到云平台中，数据库添加一条回测记录。
//                StrategyVersion strategyVersion = transactionService.strategyToCloud(request);
//
//                if (strategyVersion != null) {
//                    LOGGER.info("===回测===历史版本保存成功，pin={}，strategyId={}, strategyVersionId={}",
//                            taskRequest.getUserPin(), taskRequest.getStrategyId(), strategyVersion.getStrategyVersionId());
//                }
//                String resultCloudKey = transactionService.saveResultFile(request);
//                LOGGER.info("===回测===上传云平台保存成功，pin={}，strategyId={}, resultCloudKey={}", taskRequest.getUserPin(), taskRequest.getStrategyId(), resultCloudKey);
//                if (StringUtils.isNotBlank(resultCloudKey)) {
//                    //向数据库中添加一条回测结果记录
//                    transactionService.saveResultRecordForPython(taskRequest, strategyVersion, resultCloudKey);
//                }
//                QuantResultUtils.setRunType(taskRequest.getUserPin(), taskRequest.getStrategyId(), taskRequest.getRequestTime(), QuantaTaskRequest.TASK_TYPE_REGRESSION, QuantResultUtils.RUN_TYPE_END);
//            } catch (Exception e) {
//                LOGGER.error("===回测===保存回测结果发生异常：{}", e.getMessage(), e);
//                QuantResultUtils.setRunType(taskRequest.getUserPin(), taskRequest.getStrategyId(), taskRequest.getRequestTime(), QuantaTaskRequest.TASK_TYPE_REGRESSION, QuantResultUtils.RUN_TYPE_EXCEPTION);
//            }
//
//            RunTaskUtils.setRunValue(taskRequest.getUserPin(), taskRequest.getStrategyId(), taskRequest.getRequestTime(), false);
//            response.success();
//            return response;
//        } catch (Exception e) {
//            LOGGER.error("===回测===transactionSharesEndForRegression异常", e);
//            response.fail(e.getMessage());
//            return response;
//        } finally {
//            String statisticsKey = StatisticsGroupUtils.getKey(taskRequest.getUserPin(), taskRequest.getStrategyId(), taskRequest.getRequestTime(), QuantaTaskRequest.TASK_TYPE_REGRESSION);
//            StatisticsGroupUtils.removeByKey(statisticsKey);  //清理缓存
//            UniverseMemoryCache.clean(KeyUtil.getUniverseCacheKey(taskRequest.getUserPin(), QuantaTaskRequest.TASK_TYPE_REGRESSION, taskRequest.getStrategyId(), taskRequest.getRequestTime()));
//        }
        return null;
    }

    /**
     * 保存策略运行结果（回测）：参数为此刻持仓
     *
     * @param saveResultRequest
     */
    @Override
    public TQuantCoreResponse saveResultByInfoPacksForRegression(TQuantSaveResultRequest saveResultRequest) throws TException {
        TQuantCoreResponse tQuantCoreResponse = new TQuantCoreResponse();
        try {
            InfoPacks infoPacks = ThriftDomainUtils.toInfoPacks(saveResultRequest);
            LOGGER.error("====保存策略运行结果====暂未实现，请勿调用！");
            tQuantCoreResponse.setSuccess(false);
            tQuantCoreResponse.setMessage("暂未实现，请勿调用！");
        } catch (Exception e) {
            LOGGER.error("保存回测运行结果出错：", e);
        }
        return tQuantCoreResponse;
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
