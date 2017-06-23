package com.jd.quant.core.service.task;

import com.jd.quant.core.domain.common.CommonResponse;
import com.jd.quant.core.domain.common.QuantTaskRequest;

/**
 * Quant Task Service
 *
 * @author Zhiguo.Chen
 */
public interface QuantTaskService {

    /**
     * 保存结果文件
     *
     * @param taskRequest
     * @return
     */
    CommonResponse saveResultFile(QuantTaskRequest taskRequest);

    /**
     * 保存回测结果记录到数据库
     *
     * @return
     */
    CommonResponse addResultRecordToDB();
}
