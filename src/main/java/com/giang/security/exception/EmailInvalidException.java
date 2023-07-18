package com.giang.security.exception;

import org.springframework.boot.context.properties.bind.validation.BindValidationException;

public class EmailInvalidException extends  Exception{

    private String invalidEmail;

    public EmailInvalidException() {

    }

    public EmailInvalidException(String email) {
        this.invalidEmail = email;
    }

    public EmailInvalidException(String message, String invalidEmail){
        super(message);
        this.invalidEmail = invalidEmail;
    }

    public String getInvalidEmail() {
        return this.invalidEmail;
    }

}
