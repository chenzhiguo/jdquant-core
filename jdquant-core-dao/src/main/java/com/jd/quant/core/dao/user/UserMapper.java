package com.jd.quant.core.dao.user;

import com.jd.quant.core.domain.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

/**
 * User Mapper
 *
 * @author Zhiguo.Chen
 */
@Mapper
public interface UserMapper {

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")   //返回主键
    int addUser(User user);

    /**
     * 根据用户名获取用户
     *
     * @param username
     * @return
     */
    User getUserByUsername(String username);
}
