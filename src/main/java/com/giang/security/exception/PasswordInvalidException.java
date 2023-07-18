package com.giang.security.exception;

public class PasswordInvalidException extends  Exception{
    private String invalidPassword;

    public PasswordInvalidException() {

    }

    public PasswordInvalidException(String invalidPassword){
        this. invalidPassword = invalidPassword;
    }

    public PasswordInvalidException(String message, String invalidPassword){
        super(message);
        this.invalidPassword  = invalidPassword;
    }

    public String getInvalidPassword() {
        return this.invalidPassword;
    }
}
