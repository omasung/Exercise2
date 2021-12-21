package com.activedge.exercise2;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    // Two users created below
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER")
                .and()
                .withUser("admin").password("{noop}password").roles("USER", "ADMIN");

    }

    // Securing the end points with HTTP Basic authentication
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/stocks/").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/api/stocks/**").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/api/stocks/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/stocks/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/stocks/").hasRole("ADMIN")
                .and()
                .csrf().disable()
                .formLogin().disable();
    }

}