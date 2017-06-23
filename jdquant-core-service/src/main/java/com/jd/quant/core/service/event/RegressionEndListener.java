package com.jd.quant.core.service.event;

import com.jd.quant.core.common.support.QuantTaskConstants;
import com.jd.quant.core.domain.common.CommonResponse;
import com.jd.quant.core.domain.common.QuantTaskRequest;
import com.jd.quant.core.service.task.QuantTaskService;
import com.jd.quant.core.service.utils.QuantResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;

/**
 * The listener for Regression end event.
 *
 * @author Zhiguo.Chen
 */
public class RegressionEndListener implements ApplicationListener<RegressionEndEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegressionEndListener.class);

    @Autowired
    @Qualifier("regressionTaskService")
    private QuantTaskService taskService;

    @Override
    public void onApplicationEvent(RegressionEndEvent regressionEndEvent) {
        QuantTaskRequest taskRequest = (QuantTaskRequest) regressionEndEvent.getSource();
        try {
            //写本地结果文件
            CommonResponse commonResponse = taskService.saveResultFile(taskRequest);

            if (commonResponse.isSuccess()) {
                //DB添加回测记录
                taskService.addResultRecordToDB();
            } else {
                LOGGER.error("===Regression End===Write result file error, strategyId:{}，message:{}", taskRequest.getStrategyId(), commonResponse.getMessage());
                throw new RuntimeException("Write result file error, strategyId:"+taskRequest.getStrategyId()+", message:"+commonResponse.getMessage());
            }

        } catch (Exception e) {
            LOGGER.error("===Regression End===Handle regression end event error, strategyId:{}, message:{}", taskRequest.getStrategyId(), e.getMessage(), e);
            QuantResultUtils.setRunType(taskRequest.getUserPin(), taskRequest.getStrategyId(), taskRequest.getRequestTime(), QuantTaskConstants.TASK_TYPE_REGRESSION, QuantTaskConstants.RUN_TYPE_EXCEPTION);
        }
    }
}
