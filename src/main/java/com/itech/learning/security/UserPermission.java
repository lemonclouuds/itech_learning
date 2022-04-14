package com.itech.learning.security;

public enum UserPermission {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    SUBJECT_READ("subject:read"),
    SUBJECT_WRITE("subject:write");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
