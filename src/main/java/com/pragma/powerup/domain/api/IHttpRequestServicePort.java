package com.pragma.powerup.domain.api;

public interface IHttpRequestServicePort {
    void setToken(String token);
    String getToken();
    void clearToken();
}
