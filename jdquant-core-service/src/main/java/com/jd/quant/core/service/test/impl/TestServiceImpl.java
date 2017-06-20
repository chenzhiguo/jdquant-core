package com.jd.quant.core.service.test.impl;

import com.jd.quant.core.domain.common.CommonResponse;
import com.jd.quant.core.common.utils.WebUtil;
import com.jd.quant.core.dao.TestMapper;
import com.jd.quant.core.domain.City;
import com.jd.quant.core.service.test.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Test Service Impl
 *
 * @author Zhiguo.Chen
 */
@Service
public class TestServiceImpl implements TestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebUtil.class);

    @Autowired
    private TestMapper testMapper;

    @Override
    public CommonResponse addCity(City city) {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();
        try {
            int id = testMapper.addCity(city);
            commonResponse.success("成功插入ID：" + id);
        } catch (Exception e) {
            LOGGER.error("操作异常：", e);
            commonResponse.fail(e.getMessage());
        }
        return commonResponse;
    }
}
