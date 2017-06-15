package com.jd.quant.core.service.thrift.handler;

import com.jd.quant.core.domain.thrift.*;
import com.jd.quant.core.service.thrift.QuantCoreService;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author Zhiguo.Chen <me@chenzhiguo.cn>
 */
@Component
public class QuantCoreServiceHandler implements QuantCoreService.Iface{

    /**
     * 策略执行异常反馈
     *
     * @param addExceptionRequest
     */
    @Override
    public TQuantCoreResponse addQuantException(TQuantAddExceptionRequest addExceptionRequest) throws TException {
        return null;
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
