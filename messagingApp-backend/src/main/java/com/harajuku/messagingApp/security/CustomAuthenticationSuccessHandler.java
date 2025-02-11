package com.harajuku.messagingApp.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.harajuku.messagingApp.repository.UserRepository;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired 
	UserRepository userRep;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
    	long id = userRep.findByUsername(authentication.getName()).getId();
    	String redirectUrl = "/landingPage/" + id; 
        response.sendRedirect(redirectUrl);
		
	}

}
