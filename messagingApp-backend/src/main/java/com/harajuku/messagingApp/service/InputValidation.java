package com.harajuku.messagingApp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.harajuku.messagingApp.model.User;
import com.harajuku.messagingApp.repository.UserRepository;

@Component
public class InputValidation {

	@Autowired
	UserRepository userRep;

//	@Autowired
////	@Lazy
//	private PasswordEncoder passwordEncoder;

	@Autowired
	public InputValidation(UserRepository userRep) {
		this.userRep = userRep;
//		this.passwordEncoder = pwEnc;
	}

	public List<String> validateRegistrationInput(String name) {
		List<String> errors = new ArrayList<>();
		if (usernameIsTaken(name)) {
			errors.add("Username is already taken");
		}
		return errors;
	}

	private boolean usernameIsTaken(String name) {
		return userRep.findByUsername(name) != null;
	}

	public List<String> verifyLoginData(String username, String password) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		List<String> errors = new ArrayList<>();
		if (!usernameIsTaken(username)) {
			errors.add("Username doesn't exist");
		} else {
			User user = userRep.findByUsername(username);
			if (!passwordEncoder.matches(password, user.getPassword())) {
				errors.add("Password is incorrect");
			}
		}
		return errors;
	}

}
