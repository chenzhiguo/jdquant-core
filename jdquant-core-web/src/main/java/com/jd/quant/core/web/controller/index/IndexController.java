package com.jd.quant.core.web.controller.index;

import com.jd.quant.core.domain.common.CommonResponse;
import com.jd.quant.core.domain.user.User;
import com.jd.quant.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统常用功能处理器
 *
 * @author Zhiguo.Chen
 */
@Controller
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private UserService userService;

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping(value = {"", "index", "index.html", "index.do"})
    public String index() {
        return "index";
    }

    /**
     * 用户登录
     *
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    /**
     * 登出系统
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "login";
    }

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("signup")
    public CommonResponse signup(@ModelAttribute User user) {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();
        try {
            commonResponse = userService.addUser(user);
        } catch (Exception e) {
            LOGGER.error("新增用户出错：{}", e.getMessage(), e);
            commonResponse.fail("新增用户出错：" + e.getMessage());
        }
        return commonResponse;
    }

}
