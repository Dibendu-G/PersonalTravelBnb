package com.travelbnb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;

@Configuration
public class SecruityConfig {

    private JWTRequestFilter jwtRequestFilter;

    public SecruityConfig(JWTRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.csrf().disable().cors().disable();
        http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);
        http.authorizeHttpRequests().anyRequest().permitAll();
//        http.authorizeHttpRequests().requestMatchers("/api/v1/appUser/addUser","/api/v1/appUser/login")
//                .permitAll()
//                .requestMatchers("/api/v1/countries/addCountry")
//                .hasRole("ADMIN")
//                .requestMatchers("/api/v1/uploadPhotos/photo")
//                .hasAnyRole("ADMIN","USER")
//                .anyRequest().authenticated();
        return http.build();
    }
}
