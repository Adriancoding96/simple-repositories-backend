/*
package com.adrian.simple_repositories.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
          .csrf(csrf -> csrf.disable())  // Disable CSRF protection
          .authorizeHttpRequests(authorizeRequests ->
              authorizeRequests
                  .anyRequest().permitAll()  // Allow all requests without authentication
          )
          .headers(headers -> headers.frameOptions().disable());  // Disable frame options to allow H2 Console access

      return http.build();
  }

}*/
