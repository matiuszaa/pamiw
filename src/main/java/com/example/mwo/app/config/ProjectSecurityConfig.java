//package com.example.mwo.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
//class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    public void  configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().mvcMatchers("/user/**").permitAll()
//                .mvcMatchers("/parking/menu").permitAll()
//                .mvcMatchers("/parking/**").hasRole("USER");
//    }
//
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("user").password("12345").roles("USER")
//                .and()
//                .withUser("admin").password("admin").roles("USER", "ADMIN");
//    }
//}
