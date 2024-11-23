package com.srinivasa_refrigeration_works.srw.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class CustomLoginSecurity {

    // Defines in-memory user details for authentication
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails user = User.builder()
                                .username("sai") // Username
                                .password("{noop}1234") // Password
                                .roles("USER") // Role assigned to the user
                                .build();
        return new InMemoryUserDetailsManager(user);
    }

    // Configures the security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        return security
                // Allows static resources without authentication
                .authorizeHttpRequests(configurer -> configurer
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .anyRequest()
                        .authenticated()) // All other requests require authentication
                // Configures custom login page
                .formLogin(form -> form
                        .loginPage("/SRW/login") // Custom login page URL
                        .loginProcessingUrl("/authenticateUser") // URL for login processing
                        .permitAll()) // Login page is accessible to all
                // Configures logout behavior
                .logout(logout -> logout
                        .permitAll()) // Logout endpoint is accessible to all
                .build();
    }
}
