package com.pragma.powerup.application.handler;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface ITokenHandler {
    UsernamePasswordAuthenticationToken getAuthenticationToken(String token);

    String getIdFromToken();
}
