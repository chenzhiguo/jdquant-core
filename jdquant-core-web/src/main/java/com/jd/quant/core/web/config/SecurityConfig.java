package com.jd.quant.core.web.config;

import com.jd.quant.core.service.user.UserService;
import com.jd.quant.core.web.security.LoginSuccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import javax.sql.DataSource;

/**
 * Security Config
 *
 * @author Zhiguo.Chen
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private UserService userService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
//                .jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("select username,password,enabled from users where username = ?")
//                .authoritiesByUsernameQuery("select username,role from user_roles where username = ?")
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/static/**", "/signup").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(loginSuccessHandler())
//                .loginProcessingUrl("login")
//                .successForwardUrl("/index")
                .permitAll()
                .and()
                .logout()
//                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
//                .logoutSuccessHandler(logoutSuccessHandler)
                .invalidateHttpSession(true)
//                .addLogoutHandler(logoutHandler)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .rememberMe().rememberMeServices(rememberMeServices());
//                .and()
//                .csrf().disable();
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        SpringSessionRememberMeServices rememberMeServices =
                new SpringSessionRememberMeServices();
        // optionally customize
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }
}
