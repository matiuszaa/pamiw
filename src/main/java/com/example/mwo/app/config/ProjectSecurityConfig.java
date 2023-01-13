package com.example.mwo.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .mvcMatchers("/menu").permitAll()
                .mvcMatchers("/parkings").authenticated()
                .mvcMatchers("/reserve").authenticated()
                .mvcMatchers("/getParkingSpot").authenticated()
                .mvcMatchers("/reservation").authenticated()
                .mvcMatchers("/release").authenticated()
                .mvcMatchers("/login").permitAll()
                .mvcMatchers("/register").permitAll()
                .mvcMatchers("/showFormForRegistry").permitAll()
                .mvcMatchers("/uploads").permitAll()
                .and().formLogin().loginPage("/login")
                .defaultSuccessUrl("/menu").failureUrl("/login?error=true").permitAll()
                .and().logout().logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll()
                .and().httpBasic();
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("user")
                .password("12345")
                .roles("USER")
                .build();
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("54321")
                .roles("USER","ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

}
