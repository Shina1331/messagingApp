package com.harajuku.messagingApp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MessageConversionException.class)
    public ResponseEntity<String> handleMessageConversionException(MessageConversionException ex) {
        return new ResponseEntity<>("Message Conversion Failed: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}