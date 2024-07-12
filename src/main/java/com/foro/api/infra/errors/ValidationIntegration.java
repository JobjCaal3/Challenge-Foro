package com.foro.api.infra.errors;

public class ValidationIntegration extends RuntimeException{
    public ValidationIntegration(String s) {
        super(s);
    }
}
