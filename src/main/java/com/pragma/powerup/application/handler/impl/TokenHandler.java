package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.handler.ITokenHandler;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pragma.powerup.domain.Constants;

import java.util.AbstractList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenHandler implements ITokenHandler {
    private static final String ACCESS_TOKEN_SECRET = "fa52f4e13bc9557eb253c02ed25d0ecaf9c37a3e";
    @Override
    public UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        Claims claims;

        try {
            claims = Jwts
                    .parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (Exception e) {
            return null;
        }

        String email =claims.getSubject();
        List<String> role = claims.get(Constants.ROLE, AbstractList.class);

        return new UsernamePasswordAuthenticationToken(
                email,
                null,
                role.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }

    @Override
    public String getIdFromToken() {
        return null;
    }


}