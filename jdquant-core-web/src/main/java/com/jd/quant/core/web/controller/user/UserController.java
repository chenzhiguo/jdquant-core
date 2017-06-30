package com.jd.quant.core.web.controller.user;

import com.jd.quant.core.domain.common.CommonResponse;
import com.jd.quant.core.domain.user.User;
import com.jd.quant.core.service.user.UserService;
import com.jd.quant.core.service.utils.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User Controller
 *
 * @author Zhiguo.Chen
 */
@Controller
@RequestMapping("user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 生成Token
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("generateToken")
    public CommonResponse generateToken(HttpServletRequest request, HttpServletResponse response) {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();
        try {
            User user = SessionUtil.getCurrentUser();
            String token = userService.generateToken(user);
            commonResponse.setData("token", token);
        } catch (Exception e) {
            LOGGER.error("Generate token error: {}", e.getMessage(), e);
            commonResponse.fail("生成Token出现异常！" + e.getMessage());
        }
        return commonResponse;
    }
}
