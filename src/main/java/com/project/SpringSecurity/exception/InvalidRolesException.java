package com.project.SpringSecurity.exception;

public class InvalidRolesException extends RuntimeException {
    public InvalidRolesException(String message) {
        super(message);
    }
}
