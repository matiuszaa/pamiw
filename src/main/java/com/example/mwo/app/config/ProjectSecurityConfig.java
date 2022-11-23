//package com.example.mwo.app.config;

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
