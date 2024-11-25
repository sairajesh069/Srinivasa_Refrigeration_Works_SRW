package com.srinivasa_refrigeration_works.srw.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;

public class CustomRoleHierarchy implements RoleHierarchy {

    @Override
    public Collection<? extends GrantedAuthority> getReachableGrantedAuthorities(
        Collection<? extends GrantedAuthority> authorities) {
        // Initialize a Set with the provided authorities to store reachable authorities
        Set<GrantedAuthority> reachableAuthorities = new HashSet<>(authorities);

        // Iterate through each granted authority (role)
        for (GrantedAuthority authority : authorities) {
            // Add specific roles to reachableAuthorities based on current authority
            switch (authority.getAuthority()) {
                // If the role is "ROLE_OWNER", add "ROLE_EMPLOYEE" and "ROLE_CUSTOMER" to reachable authorities
                case "ROLE_OWNER" -> {
                    reachableAuthorities.add(() -> "ROLE_EMPLOYEE");
                    reachableAuthorities.add(() -> "ROLE_CUSTOMER");
                }
                // If the role is "ROLE_EMPLOYEE", add "ROLE_CUSTOMER" to reachable authorities
                case "ROLE_EMPLOYEE" -> reachableAuthorities.add(() -> "ROLE_CUSTOMER");
            }
        }
        // Return the updated collection of reachable authorities
        return reachableAuthorities;
    }
}