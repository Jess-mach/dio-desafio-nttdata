package com.nttdata.catalogo.exception;

class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}