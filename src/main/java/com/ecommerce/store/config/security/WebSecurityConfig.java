package com.ecommerce.store.config.security;

import com.ecommerce.store.service.impl.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {


    private static final String[] FREE_ACCESS_PATTERN = {
            "/customer/**",
            "/auth/**",
            "/login/**",
    };
    private static final String[] IGNORE_PATTERN = {
            "/v2/api-docs",
            "/swagger-ui.html"
    };
    private static final String[] USER_ROLE_PATTERN = {
            "/user/**"
    };
    private static final String[] ADMIN_ROLE_PATTERN = {
            "/admin/**"
    };
    private final UserDetailServiceImpl userDetailService;
    private final JwtEntryPoint jwtEntryPoint;

    @Bean
    public JwtAuthTokenFilter jwtAuthTokenFilter() {
        return new JwtAuthTokenFilter();
    }


    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);

        return providerManager;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(FREE_ACCESS_PATTERN).permitAll()
                                .requestMatchers(IGNORE_PATTERN).permitAll()
                                .requestMatchers(USER_ROLE_PATTERN).hasRole("USER")
                                .requestMatchers(ADMIN_ROLE_PATTERN).hasRole("ADMIN")
                                .anyRequest().permitAll()
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();

    }
}
