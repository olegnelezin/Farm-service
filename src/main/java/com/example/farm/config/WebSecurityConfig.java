package com.example.farm.config;

import com.example.farm.filter.AuthFilter;
import com.example.farm.filter.CookieAuthFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
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
    private final AuthFilter authFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.exceptionHandling((exceptions) -> exceptions
                .authenticationEntryPoint(authEntryPoint)
        )
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/auth/login", "auth/refresh-token").permitAll()
                        .requestMatchers("/admin/register").hasAuthority(ADMIN)
                        .requestMatchers("/employee").hasAuthority(EMPLOYEE)
                        .anyRequest().authenticated()
                );
        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new CookieAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(authenticationProvider);

        return http.build();
    }

}
