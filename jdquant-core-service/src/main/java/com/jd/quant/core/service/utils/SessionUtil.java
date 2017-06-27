package com.jd.quant.core.service.utils;

import com.jd.quant.core.domain.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Session Util
 *
 * @author Zhiguo.Chen
 */
public class SessionUtil {

    private static final Authentication authentication;

    static {
        authentication = SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getUserName() {
        return authentication.getName();
    }

    public static User getUser() {
        return (User) authentication.getPrincipal();
    }


}