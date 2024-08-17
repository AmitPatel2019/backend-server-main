package com.asdsoft.mavala.web;

import com.asdsoft.mavala.entity.UserMawala;
import com.asdsoft.mavala.exception.AuthException;
import com.asdsoft.mavala.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class BaseController {
    @Autowired
    private UserService userService;

    protected Jwt getUser() {
        try {
            return (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new AuthException();
        }
    }

    protected UserMawala getUserData() {
        try {
            Jwt jwt = getUser();
            return userService.getUser(jwt);
        } catch (Exception e) {
            throw new AuthException();
        }
    }
}

