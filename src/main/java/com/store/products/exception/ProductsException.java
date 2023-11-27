package com.store.products.exception;

public class ProductsException extends RuntimeException {

    public ProductsException(String message) {
        throw new RuntimeException(message);
    }

    public ProductsException(String message, Exception e) {
        throw new RuntimeException(message, e);
    }
}
