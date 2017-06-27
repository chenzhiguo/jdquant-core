package com.jd.quant.core.web.controller.index;

import com.jd.quant.core.domain.common.CommonResponse;
import com.jd.quant.core.domain.user.User;
import com.jd.quant.core.service.user.UserService;
import com.jd.quant.core.service.utils.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
//    @RequestMapping(value = "logout", method = RequestMethod.GET)
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        return "login";
//    }

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping("signup")
    public CommonResponse signup(@ModelAttribute User user, BindingResult bindingResult) {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();
        try {
            if (bindingResult.hasErrors()) {
                commonResponse.fail("表单验证失败！", bindingResult.getFieldErrors());
            } else {
                commonResponse = userService.addUser(user);
            }
        } catch (Exception e) {
            LOGGER.error("新增用户出错：{}", e.getMessage(), e);
            commonResponse.fail("新增用户出错：" + e.getMessage());
        }
        return commonResponse;
    }

    @ResponseBody
    @GetMapping("main")
    public CommonResponse main(@ModelAttribute User user, BindingResult bindingResult) {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();
        try {
            String username = SessionUtil.getUserName();
            if (bindingResult.hasErrors()) {
                commonResponse.fail("表单验证失败！", bindingResult.getFieldErrors());
            } else {
                commonResponse = userService.addUser(user);
            }
        } catch (Exception e) {
            LOGGER.error("新增用户出错：{}", e.getMessage(), e);
            commonResponse.fail("新增用户出错：" + e.getMessage());
        }
        return commonResponse;
    }

}
