package com.jd.quant.core.service.user;

import com.jd.quant.core.domain.common.CommonResponse;
import com.jd.quant.core.domain.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.validation.constraints.NotNull;

/**
 * User Service
 *
 * @author Zhiguo.Chen
 */
public interface UserService extends UserDetailsService {

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    CommonResponse addUser(@NotNull User user);

    /**
     * 生成Token
     *
     * @param user
     */
    String generateToken(@NotNull User user);

    /**
     * 获取用户Token
     *
     * @param username
     * @return
     */
    String getUserToken(@NotNull String username);

    /**
     * 根据Token获取用户
     *
     * @param token
     * @return
     */
    User getUserByToken(@NotNull String token);
}
