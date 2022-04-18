package com.itech.learning.controller;

import lombok.Data;

@Data
public class AuthenticationRequestDto {
    private String username;
    private String password;
}
