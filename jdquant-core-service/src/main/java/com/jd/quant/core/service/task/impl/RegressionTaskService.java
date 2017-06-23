package com.jd.quant.core.service.task.impl;

import com.jd.quant.core.domain.common.CommonResponse;
import com.jd.quant.core.domain.common.QuantTaskRequest;
import com.jd.quant.core.service.task.QuantTaskService;

/**
 * Regression task handle service
 *
 * @author Zhiguo.Chen
 */
public class RegressionTaskService implements QuantTaskService {

    /**
     * 保存结果文件
     *
     * @param taskRequest
     * @return
     */
    @Override
    public CommonResponse saveResultFile(QuantTaskRequest taskRequest) {
        return null;
    }

    /**
     * 保存回测结果记录到数据库
     *
     * @return
     */
    @Override
    public CommonResponse addResultRecordToDB() {
        return null;
    }
}
