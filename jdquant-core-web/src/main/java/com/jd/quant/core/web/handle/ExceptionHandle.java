package com.jd.quant.core.web.handle;

import com.jd.quant.core.domain.common.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 *
 * @author Zhiguo.Chen
 */
@ControllerAdvice(annotations = ResponseBody.class)
public class ExceptionHandle {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandle.class);

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public CommonResponse commonExceptionHandle(Exception e) {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();
        LOGGER.error("[统一异常处理]系统异常：", e);
        return commonResponse.fail("系统异常，请稍后再试！");
    }
}
