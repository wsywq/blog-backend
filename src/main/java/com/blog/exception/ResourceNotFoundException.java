package com.blog.exception;

/**
 * 资源未找到异常
 * 
 * @author blog
 * @since 2024-01-01
 */
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
