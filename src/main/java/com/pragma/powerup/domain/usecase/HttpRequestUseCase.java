package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IHttpRequestServicePort;
import com.pragma.powerup.domain.spi.IHttpRequestPersistencePort;

public class HttpRequestUseCase implements IHttpRequestServicePort {
    private final IHttpRequestPersistencePort httpRequestPersistencePort;


    public HttpRequestUseCase(IHttpRequestPersistencePort httpRequestPersistencePort) {
        this.httpRequestPersistencePort = httpRequestPersistencePort;
    }

    @Override
    public void setToken(String token) {
        httpRequestPersistencePort.setToken(token);
    }

    @Override
    public String getToken() {
        return httpRequestPersistencePort.getToken();
    }

    @Override
    public void clearToken() {
        httpRequestPersistencePort.clearToken();
    }
}
