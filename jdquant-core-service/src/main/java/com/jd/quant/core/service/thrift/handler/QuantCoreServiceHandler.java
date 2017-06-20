package com.jd.quant.core.service.thrift.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jd.quant.core.common.support.QuantTaskRunType;
import com.jd.quant.core.domain.thrift.*;
import com.jd.quant.core.service.thrift.QuantCoreService;
import com.jd.quant.core.service.utils.QuantResultUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Description:
 *
 * @author Zhiguo.Chen <me@chenzhiguo.cn>
 */
@Component
public class QuantCoreServiceHandler implements QuantCoreService.Iface{

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
        return null;
    }

    /**
     * 保存策略运行结果（回测）：参数为此刻持仓
     *
     * @param saveResultRequest
     */
    @Override
    public TQuantCoreResponse saveResultByInfoPacksForRegression(TQuantSaveResultRequest saveResultRequest) throws TException {
        return null;
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
