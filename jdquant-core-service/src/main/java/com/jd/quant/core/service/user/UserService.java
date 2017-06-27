package com.jd.quant.core.service.user;

import com.jd.quant.core.domain.common.CommonResponse;
import com.jd.quant.core.domain.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * User Service
 *
 * @author Zhiguo.Chen
 */
public interface UserService extends UserDetailsService {

    CommonResponse addUser(User user);
}
