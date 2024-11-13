package com.kl.book.handler;

import com.kl.book.book.exception.OperationNotPermittedException;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

import static com.kl.book.handler.BusinessErrorCodes.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException exp) {
        ExceptionResponse response = new ExceptionResponse();
        response.setBusinessErrorCode(ACCOUNT_LOCKED.getCode());
        response.setBusinessErrorDescription(ACCOUNT_LOCKED.getDescription());
        response.setError(exp.getMessage());
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(response);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException exp) {
        ExceptionResponse response = new ExceptionResponse();
        response.setBusinessErrorCode(ACCOUNT_DISABLED.getCode());
        response.setBusinessErrorDescription(ACCOUNT_DISABLED.getDescription());
        response.setError(exp.getMessage());
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException exp) {
        ExceptionResponse response = new ExceptionResponse();
        response.setBusinessErrorCode(BAD_CREDENTIALS.getCode());
        response.setBusinessErrorDescription(BAD_CREDENTIALS.getDescription());
        response.setError(exp.getMessage());
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(response);
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException exp) {
        ExceptionResponse response = new ExceptionResponse();

        response.setError(exp.getMessage());
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(response);
    }
    @ExceptionHandler(OperationNotPermittedException.class)
    public ResponseEntity<ExceptionResponse> handleException(OperationNotPermittedException exp) {
        ExceptionResponse response = new ExceptionResponse();

        response.setError(exp.getMessage());
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exp) {
        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors()
                .forEach(error ->
                {
                    var errorMessage = error.getDefaultMessage();
                    errors.add(errorMessage);
                });

        ExceptionResponse response = new ExceptionResponse();
        response.setValidationErrors(errors);
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exp) {

        //log the exception
        exp.printStackTrace();
        ExceptionResponse response = new ExceptionResponse();
        response.setBusinessErrorDescription("Internal error, contact the admin");
        response.setError(exp.getMessage());
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(response);
    }


}
