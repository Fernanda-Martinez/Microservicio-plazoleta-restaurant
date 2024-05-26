package com.pragma.powerup.infrastructure.jwt;

import com.pragma.powerup.application.handler.ITokenHandler;
import com.pragma.powerup.domain.spi.IHttpRequestPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final ITokenHandler tokenHandler;
    private final IHttpRequestPersistencePort httpRequestPersistencePort;
    private static final String BEARER_TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal
            (
                    HttpServletRequest request,
                    HttpServletResponse response,
                    FilterChain filterChain
            ) throws ServletException, IOException {
        final String token = getTokenRequest(request);

        if(token != null){
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    tokenHandler.getAuthenticationToken(token);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        filterChain.doFilter(request, response);

    }

    private String getTokenRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(StringUtils.hasText(authHeader) && authHeader.startsWith(BEARER_TOKEN_PREFIX)){
            httpRequestPersistencePort.setToken(authHeader);
            return authHeader.substring(7);
        }
        return null;
    }
}
