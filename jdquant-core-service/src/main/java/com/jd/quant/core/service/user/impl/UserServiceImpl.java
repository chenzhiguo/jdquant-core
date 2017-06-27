package com.jd.quant.core.service.user.impl;

import com.google.common.collect.Sets;
import com.jd.quant.core.dao.user.UserMapper;
import com.jd.quant.core.dao.user.UserRoleMapper;
import com.jd.quant.core.domain.common.CommonResponse;
import com.jd.quant.core.domain.user.User;
import com.jd.quant.core.domain.user.UserRole;
import com.jd.quant.core.service.user.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * User Service Impl
 *
 * @author Zhiguo.Chen
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @HystrixCommand
    @Override
    public CommonResponse addUser(User user) {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();
        User oldUser = userMapper.getUserByUsername(user.getUsername());
        if (null != oldUser) {
            LOGGER.error("已存在该用户，请更换用户名！");
            commonResponse.fail("已存在该用户，请更换用户名！");
        } else {
            //对密码进行加密
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodePassword = encoder.encode(user.getPassword());
            user.setPassword(encodePassword);
            userMapper.addUser(user);
            commonResponse.success("注册用户成功！");
        }
        return commonResponse;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            List<UserRole> userRoles = userRoleMapper.getUserRoleByUsername(username);
            if (!CollectionUtils.isEmpty(userRoles)) {
                Set<GrantedAuthority> authorities = Sets.newHashSet();
                for (UserRole userRole : userRoles) {
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.getRole());
                    authorities.add(authority);
                }
                user.setAuthorities(authorities);
            }
            return user;
        }
    }
}
