package com.lujx3419.usersystem.dto.request;

public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;

    // getters and setters
    public String getOldPassword() {
        return oldPassword;
    }
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
