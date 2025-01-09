package com.fredrick.financial_management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private CustomAuthEntryPoint customAuthEntryPoint;
    @Autowired
    private JwtAuthFilter jwtAuthFilter;
    @Autowired
    private CorsConfig config;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(configurer -> configurer.requestMatchers("/api/x-account/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/x-item/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(c -> c.configurationSource(config.corsConfigurationSource()))
                .authenticationProvider(authenticationProvider)
                .exceptionHandling(exc -> exc.authenticationEntryPoint(customAuthEntryPoint))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//        http.headers(header -> header.addHeaderWriter((request, response) -> response.setHeader(
//                        "X-XSS-Protection",
//                        "1; mode=block"))
//                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
//                .contentSecurityPolicy(ContentSecurityPolicy -> ContentSecurityPolicy.policyDirectives(
//                        "frame-ancestors 'self'")));

        return http.build();
    }
}
