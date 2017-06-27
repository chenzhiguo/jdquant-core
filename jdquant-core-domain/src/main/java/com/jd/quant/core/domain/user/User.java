package com.jd.quant.core.domain.user;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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

    private String username;

    private String password;

    private String realName;

    private boolean enabled;

    private String email;

    private String telephone;

    private String token;

    private Date createTime;

    private Set<GrantedAuthority> authorities;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
