package com.example.anticafe.exception;

public final class RoleNotFoundException extends RuntimeException{

    public RoleNotFoundException() {
        super("Role not found");
    }
}
