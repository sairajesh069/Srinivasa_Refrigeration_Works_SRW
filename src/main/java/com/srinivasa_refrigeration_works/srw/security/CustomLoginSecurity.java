package com.srinivasa_refrigeration_works.srw.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Marks this class as a configuration class
public class CustomLoginSecurity {

    // Bean to configure the userDetailsManager using JdbcUserDetailsManager
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        
        // Custom query to retrieve user details by username
        String usersByUserNameQuery = """
                SELECT username, password, enabled
                FROM user_credentials
                WHERE username=?;
                """;

        // Custom query to retrieve user authorities (roles) by username
        String authoritiesByUserNameQuery = """
                SELECT username, roles
                FROM user_roles 
                WHERE username=?;
                """;

        userDetailsManager.setUsersByUsernameQuery(usersByUserNameQuery);
        userDetailsManager.setAuthoritiesByUsernameQuery(authoritiesByUserNameQuery);

        return userDetailsManager; // Return the userDetailsManager bean
    }

    // Bean to configure and provide a custom RoleHierarchy implementation 
    @Bean
    public RoleHierarchy roleHierarchy() {
        return new CustomRoleHierarchy(); // Returns a custom implementation of RoleHierarchy, which defines the role hierarchy for user permissions
    }

    // Bean to provide a BCryptPasswordEncoder for password hashing
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Returns a new BCryptPasswordEncoder instance for password encoding
    }

    // Bean to configure the security filter chain for HTTP security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        return security
                // Configure which requests are allowed without authentication
                .authorizeHttpRequests(configurer -> configurer
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() // Static resources
                        .requestMatchers("/", "/SRW/home").permitAll() // Home page is accessible to all
                        .requestMatchers("/SRW/customer-register", "/SRW/customer-confirmation").permitAll() // Customer creation and confirmation pages are accessible to all
                        .requestMatchers("/SRW/username-recovery", "/SRW/password-reset").permitAll() // username recovery and password reset pages are accessible to all
                        .requestMatchers("/SRW/owner-register", "/SRW/owner-confirmation").hasRole("OWNER")  // Restrict access to these URLs to users who have the "OWNER" role
                        .requestMatchers("/SRW/employee-register", "/SRW/employee-confirmation").hasRole("EMPLOYEE")  // Restrict access to these URLs to users who have the "EMPLOYEE" role
                        .anyRequest()
                        .authenticated()) // All other requests require authentication
                // Configure custom login page
                .formLogin(form -> form
                        .loginPage("/SRW/login") // Custom login page URL
                        .loginProcessingUrl("/authenticateUser") // URL for processing the login form
                        .permitAll()) // The login page is accessible to everyone
                // Configure logout behavior
                .logout(logout -> logout
                        .permitAll()) // Logout is accessible to everyone
                .exceptionHandling(customizer -> customizer.accessDeniedPage("/SRW/access-denied"))  // Customize the access-denied page URL to redirect unauthorized users to "/SRW/access-denied"
                .build(); // Build the security filter chain
    }
}
