package com.example.farm.config;

import com.example.farm.filter.AuthJwtFilter;
import com.example.farm.util.WebSecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {
    private static final String EMPLOYEE = "EMPLOYEE";
    private static final String ADMIN = "ADMIN";
    private final AuthEntryPoint authEntryPoint;
    private final AuthJwtFilter authJwtFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.exceptionHandling((exceptions) -> exceptions
                .authenticationEntryPoint(authEntryPoint)
        )
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.GET, WebSecurityUtils.employeeMappingsGET).hasAnyAuthority(EMPLOYEE)
                        .requestMatchers(HttpMethod.POST, WebSecurityUtils.employeeMappingsPOST).hasAnyAuthority(EMPLOYEE)
                        .requestMatchers(HttpMethod.GET, WebSecurityUtils.adminMappingsGET).hasAnyAuthority(ADMIN)
                        .requestMatchers(HttpMethod.POST, WebSecurityUtils.adminMappingsPOST).hasAnyAuthority(ADMIN)
                        .requestMatchers(WebSecurityUtils.publicMappings).permitAll()
                        .anyRequest().authenticated()
                );
        http.addFilterBefore(authJwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
