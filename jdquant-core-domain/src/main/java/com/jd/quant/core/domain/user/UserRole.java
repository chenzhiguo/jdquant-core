package com.jd.quant.core.domain.user;

import lombok.Data;

import java.util.Date;

/**
 * Description
 *
 * @author Zhiguo.Chen
 */
@Data
public class UserRole {

    private Long id;

    private String username;

    private String role;

    private Date createTime;

}
