package com.nttdata.pedidos.exception;

class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}