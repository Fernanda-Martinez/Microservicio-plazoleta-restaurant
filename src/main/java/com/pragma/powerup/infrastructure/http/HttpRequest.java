package com.pragma.powerup.infrastructure.http;

import com.pragma.powerup.domain.spi.IHttpRequestPersistencePort;

public class HttpRequest implements IHttpRequestPersistencePort {

    private static final ThreadLocal<String> TOKEN = new ThreadLocal<>();
    @Override
    public void setToken(String token) {
        TOKEN.set(token);
    }

    @Override
    public String getToken() {
        String token = TOKEN.get();
        return token;
    }

    @Override
    public void clearToken() {
        TOKEN.remove();
    }
}
