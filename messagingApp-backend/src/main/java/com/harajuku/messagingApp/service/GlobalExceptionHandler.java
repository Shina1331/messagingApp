package com.harajuku.messagingApp.service;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
//This is for all the Controllers in the application!
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleGlobalExceptionInController(Exception ex, Model model) {
    	model.addAttribute("errorMessage", ex.getMessage());
    	return "errorPage";
    }
    
    
}