package com.jd.quant.core.dao.user;

import com.jd.quant.core.domain.user.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

/**
 * User Role Mapper
 *
 * @author Zhiguo.Chen
 */
@Mapper
public interface UserRoleMapper {

    /**
     * 添加用户角色
     *
     * @param userRole
     * @return
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addUserRole(UserRole userRole);

    /**
     * 根据用户名获取用户角色
     *
     * @param username
     * @return
     */
    List<UserRole> getUserRoleByUsername(String username);
}
