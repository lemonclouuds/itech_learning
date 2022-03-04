package com.itech.learning.domain;

import com.itech.learning.security.UserPermission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum UserRole {
    ADMIN(Set.of(UserPermission.COURSE_WRITE, UserPermission.COURSE_READ,
                UserPermission.STUDENT_WRITE, UserPermission.STUDENT_READ)),
    STUDENT(Set.of(UserPermission.COURSE_WRITE, UserPermission.COURSE_READ));

    @Getter
    private final Set<UserPermission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream().map(
                permission -> new SimpleGrantedAuthority(permission.getPermission())
        ).collect(Collectors.toSet());
    }
}
