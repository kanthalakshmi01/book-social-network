package com.kl.book.handler;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum BusinessErrorCodes {

    NO_CODE(0,NOT_IMPLEMENTED,"No code"),
    ACCOUNT_LOCKED(302,FORBIDDEN,"User account is locked"),
    INCORRECT_CURRENT_PASSWORD(300,BAD_REQUEST,"Current password is incorrect"),
    NEW_PASSWORD_DOES_NOT_MATCH(301,BAD_REQUEST,"The new password does not match"),
    ACCOUNT_DISABLED(303,BAD_REQUEST,"User account is disabled"),
    BAD_CREDENTIALS(304,BAD_REQUEST,"Login and/or password is incorrect");

    private final int code;
    private final String description;
    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code, HttpStatus httpStatus,String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
