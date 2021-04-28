package com.vicras.config;

import com.vicras.entity.Role;
import com.vicras.security.jwt.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userService;
    private final JwtTokenFilter tokenFilter;
    private final String[] AUTHORITY_REQUIRED_TRADER = {
            "/object",
            "/object/{\\d+}",
            "/object/my",
    };

    private final String[] AUTHORITY_REQUIRED_ADMIN = {
            "/object/for_approve",
            "/object/approve",
            "/object/decline",
            "/comment/for_approve",
            "/comment/approve",
            "/comment/decline",
    };

    @Autowired
    public SecurityConfig(UserDetailsService userService,
                          JwtTokenFilter tokenFilter) {
        this.userService = userService;
        this.tokenFilter = tokenFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/object").permitAll()
                .antMatchers(AUTHORITY_REQUIRED_ADMIN).hasAuthority(Role.ADMIN.name())
                .antMatchers(AUTHORITY_REQUIRED_TRADER).hasAuthority(Role.TRADER.name())
                .and()
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
