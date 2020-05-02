package com.brunoflaviof.resistance.rest.configuration;

import com.brunoflaviof.resistance.rest.filter.AuthenticationFilter;
import com.brunoflaviof.resistance.rest.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    private UserRepo users;

    @Autowired
    public FilterConfiguration(UserRepo users){
        this.users = users;
    }

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter(){
        FilterRegistrationBean<AuthenticationFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthenticationFilter(users));
        registrationBean.addUrlPatterns("/lobby/*");
        registrationBean.addUrlPatterns("/lobbies/*");

        return registrationBean;
    }
}
