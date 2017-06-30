package com.jd.quant.core.service.user.impl;

import com.google.common.collect.Sets;
import com.jd.quant.core.common.exception.ServiceException;
import com.jd.quant.core.common.utils.BeanJsonUtil;
import com.jd.quant.core.common.utils.CollectionUtil;
import com.jd.quant.core.common.utils.StringUtil;
import com.jd.quant.core.common.utils.UUIDUtil;
import com.jd.quant.core.dao.redis.RedisDao;
import com.jd.quant.core.dao.user.UserMapper;
import com.jd.quant.core.dao.user.UserRoleMapper;
import com.jd.quant.core.domain.common.CommonResponse;
import com.jd.quant.core.domain.user.User;
import com.jd.quant.core.domain.user.UserRole;
import com.jd.quant.core.service.user.UserService;
import com.jd.quant.core.service.utils.KeyUtil;
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

import java.util.Date;
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

    @Autowired
    private RedisDao redisDao;

//    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @Override
//    @HystrixCommand
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
    @HystrixCommand
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

    /**
     * 生成Token
     *
     * @param user
     */
    @Override
//    @HystrixCommand
    public String generateToken(User user) {
        String token = UUIDUtil.createUUID();
        user.setToken(token);
        userMapper.update(user);
        //Redis存放用户Token一周
        redisDao.setex(KeyUtil.getUserTokenKey(user.getUsername()), token, 604800);
        //用户信息保存
        String userJson = BeanJsonUtil.bean2Json(user);
        redisDao.setex(token, userJson, 604800);
        return token;
    }

    /**
     * 获取用户Token
     *
     * @param username
     * @return
     */
    @Override
    @HystrixCommand
    public String getUserToken(String username) {
        String token = redisDao.get(KeyUtil.getUserTokenKey(username));
        if (StringUtil.isBlank(token)) {
            User user = loadUserByUsername(username);
            token = user.getToken();
            //Redis存放用户Token一周
            redisDao.setex(KeyUtil.getUserTokenKey(username), token, 604800);
        }
        return token;
    }

    /**
     * 根据Token获取用户
     *
     * @param token
     * @return
     */
    @Override
    @HystrixCommand
    public User getUserByToken(String token) {
        String userJson = redisDao.get(token);
        if (StringUtil.isBlank(userJson)) {
            User searchCondition = new User();
            searchCondition.setToken(token);
            List<User> users = userMapper.getUsers(searchCondition);
            if (CollectionUtil.isEmpty(users)) {
                return null;
            }
            if (users.size() > 1) {
                throw new ServiceException("根据Token获取用户出错！获取数量：" + users.size());
            }
            User result = users.get(0);
            //缓存用户信息
            redisDao.setex(token, BeanJsonUtil.bean2Json(result), 604800);
            return users.get(0);
        }
        return BeanJsonUtil.json2Object(userJson, User.class);
    }

}
