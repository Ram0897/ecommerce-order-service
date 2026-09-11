package com.ram.ecommerce.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean UserDetailsService users(@Value("${app.security.username:admin}") String username,@Value("${app.security.password:change-me}") String password,PasswordEncoder encoder){return new InMemoryUserDetailsManager(User.withUsername(username).password(encoder.encode(password)).roles("USER").build());}
 @Bean SecurityFilterChain filterChain(HttpSecurity http,JwtAuthenticationFilter jwt) throws Exception {
  return http.csrf(c->c.disable()).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(a->a.requestMatchers("/api/v1/auth/**","/swagger-ui/**","/swagger-ui.html","/v3/api-docs/**","/actuator/health").permitAll().anyRequest().authenticated()).addFilterBefore(jwt, UsernamePasswordAuthenticationFilter.class).build();
 }
}
