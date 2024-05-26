package com.pragma.powerup.domain.spi;

public interface IHttpRequestPersistencePort {
    void setToken(String token);
    String getToken();
    void clearToken();
}
