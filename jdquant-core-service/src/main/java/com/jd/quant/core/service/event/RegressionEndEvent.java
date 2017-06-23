package com.jd.quant.core.service.event;

import com.jd.quant.core.domain.common.QuantTaskRequest;
import org.springframework.context.ApplicationEvent;

/**
 * End event for regression testing, it will triggers a series of methods.
 * Such as save result record to database.
 *
 * @author Zhiguo.Chen
 */
public class RegressionEndEvent extends ApplicationEvent {

    public RegressionEndEvent(QuantTaskRequest taskRequest) {
        super(taskRequest);
    }
}
