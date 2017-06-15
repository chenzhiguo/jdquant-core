package com.jd.quant.core.controller.index;

import com.jd.quant.core.common.support.CommonResponse;
import com.jd.quant.core.domain.City;
import com.jd.quant.core.service.test.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description:
 *
 * @author Zhiguo.Chen
 */
@Controller
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private TestService testService;

    @RequestMapping("")
    public String index() {
        return "index";
    }

    @ResponseBody
    @RequestMapping("addCity")
    public CommonResponse addCity(String cityName) {
        City city = new City();
        city.setCityName(cityName);
        city.setDescription("TEST");
        city.setProvinceId(533L);
        return testService.addCity(city);
    }

}
