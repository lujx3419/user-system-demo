package com.lujx3419.usersystem.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {

    /**
     * Get the username of the current authenticated user
     */
    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return null;
    }

    /**
     * Check if the current user is an administrator
     */
    public static boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities().stream()
                    .anyMatch(authority -> "ADMIN".equals(authority.getAuthority()));
        }
        return false;
    }

    /**
     * Check if the current user has permission to access the specified user's data
     */
    public static boolean canAccessUser(String targetUsername) {
        String currentUsername = getCurrentUsername();
        return currentUsername != null && 
               (currentUsername.equals(targetUsername) || isAdmin());
    }
} 