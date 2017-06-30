package com.jd.quant.core.domain.user;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * User Entity
 *
 * @author Zhiguo.Chen
 */
@Data
public class User implements UserDetails {

    private Long id;

    @Size(min=2, max=30)
    @NotBlank(message = "用户名不可为空！")
    private String username;

    @NotBlank(message = "密码不可为空！")
    private String password;

    @NotBlank(message = "姓名不可为空！")
    private String realName;

    @NotNull
    private boolean enabled = true;

    private String email;

    @NotBlank(message = "手机号不可为空！")
    private String telephone;

    private String token;

    private Date createTime;

    private Date updateTime;

    private Set<GrantedAuthority> authorities;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
