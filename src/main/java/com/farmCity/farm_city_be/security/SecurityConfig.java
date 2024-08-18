package com.farmCity.farm_city_be.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;
@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    private final JwtAuthEntryPoint authEntryPoint;
    public SecurityConfig(JwtAuthEntryPoint authEntryPoint) {
        this.authEntryPoint = authEntryPoint;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()));
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(configure ->
                configure
                        .requestMatchers(antMatcher("/swagger-ui/**")).permitAll()
                        .requestMatchers(antMatcher("/javainuse-openapi/**")).permitAll()
                        // Authorizations
                        .requestMatchers(antMatcher(HttpMethod.GET, "/actuator/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/user-mgmt-service/signup")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/user-mgmt-service/login")).permitAll()
                        .requestMatchers(antMatcher( "/api/v1/user-mgmt-service/otp/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/v1/user-mgmt-service/reset_password/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/user-mgmt-service/reset_password/password_update")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/user-mgmt-service/profile/**")).hasAnyAuthority("USER", "DRIVER", "ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/v1/user-mgmt-service/profile/**")).hasAnyAuthority("USER", "DRIVER", "ADMIN")

                        .requestMatchers(antMatcher("/ws/**")).permitAll()
                        .requestMatchers(antMatcher("/app/**")).permitAll()
                        .requestMatchers(antMatcher("/topic/**")).permitAll()
                        .requestMatchers(antMatcher("/user/**")).permitAll()
                        .requestMatchers(antMatcher("/chatroom/**")).permitAll()
                        .requestMatchers(antMatcher("/message/**")).permitAll()
                        .requestMatchers(antMatcher("/private-message/**")).permitAll()
                        .requestMatchers(antMatcher("/private/**")).permitAll()
                        .anyRequest().authenticated());
        http.httpBasic(HttpBasicConfigurer::disable);
        http.csrf(CsrfConfigurer::disable);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(){
        return new JWTAuthenticationFilter();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of("https://primer-space.onrender.com", "http://localhost:2013", "localhost:2013"));
        configuration.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
