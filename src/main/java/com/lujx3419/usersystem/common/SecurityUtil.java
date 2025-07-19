package com.lujx3419.usersystem.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {

    /**
     * 获取当前登录用户的用户名
     */
    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return null;
    }

    /**
     * 检查当前用户是否是管理员
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
     * 检查当前用户是否有权限访问指定用户的数据
     */
    public static boolean canAccessUser(String targetUsername) {
        String currentUsername = getCurrentUsername();
        return currentUsername != null && 
               (currentUsername.equals(targetUsername) || isAdmin());
    }
} 