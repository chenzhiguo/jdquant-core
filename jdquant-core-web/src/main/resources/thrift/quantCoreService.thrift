include "domains.thrift"

namespace java com.jd.quant.core.service.thrift

/**
* Quant Core Service
**/
service QuantCoreService{

    /**
    * 策略执行异常反馈
    **/
    domains.TQuantCoreResponse addQuantException(1:domains.TQuantAddExceptionRequest addExceptionRequest);

    /**
    * 保存策略运行结果（回测）参数为结果
    **/
    domains.TQuantCoreResponse saveResult(1:domains.TQuantSaveResultTaskRequest saveResultRequest);

    /**
    * 保存策略运行结果（回测）：参数为此刻持仓
    **/
    domains.TQuantCoreResponse saveResultByInfoPacksForRegression(1:domains.TQuantSaveResultRequest saveResultRequest);

    /**
    * 保存策略运行结果（模拟）：参数为此刻持仓
    **/
    domains.TQuantCoreResponse saveResultByInfoPacksForSimulation(1:domains.TQuantSaveResultRequest saveResultRequest);

    /**
    * 获取持仓信息
    **/
    domains.TQuantGetInfoResponse getInfoPacks(1:domains.TQuantTaskRequest taskRequest);

    /**
    * 获取策略运行历史结果
    **/
    domains.TQuantGetResultResponse getHistoryResult(1:domains.TQuantTaskRequest taskRequest);
}
