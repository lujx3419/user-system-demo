package com.lujx3419.usersystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable()) // 关闭 CSRF 校验（适配 Postman）
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // 所有请求放行
            )
            .httpBasic(Customizer.withDefaults())
            .build();
    }
}
