package com.itech.learning.domain;

import com.itech.learning.security.UserPermission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum UserRole {
    ADMIN(Set.of(UserPermission.SUBJECT_WRITE, UserPermission.SUBJECT_READ,
                UserPermission.USER_WRITE, UserPermission.USER_READ)),
    STUDENT(Set.of(UserPermission.SUBJECT_READ, UserPermission.USER_READ));

    @Getter
    private final Set<UserPermission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream().map(
                permission -> new SimpleGrantedAuthority(permission.getPermission())
        ).collect(Collectors.toSet());
    }
}
