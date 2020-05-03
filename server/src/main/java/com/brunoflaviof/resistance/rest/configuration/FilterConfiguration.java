package com.brunoflaviof.resistance.rest.configuration;

import com.brunoflaviof.resistance.rest.filter.AuthenticationFilter;
import com.brunoflaviof.resistance.rest.jwt.JWTUtil;
import com.brunoflaviof.resistance.rest.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    private final UserRepo users;
    private final JWTUtil jwtUtil;

    @Autowired
    public FilterConfiguration(UserRepo users, JWTUtil jwtUtil){
        this.users = users;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter(){
        FilterRegistrationBean<AuthenticationFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthenticationFilter(users, jwtUtil));
        registrationBean.addUrlPatterns("/lobby/*");
        registrationBean.addUrlPatterns("/lobbies/*");

        return registrationBean;
    }
}
