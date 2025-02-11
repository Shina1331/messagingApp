package com.harajuku.messagingApp.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.harajuku.messagingApp.controller.InputValidation;
import com.harajuku.messagingApp.model.User;
import com.harajuku.messagingApp.service.UserService;

@Controller
@RequestMapping("/register")
public class RegisterPage {

	@Autowired
	UserService userServ;
	@Autowired
	InputValidation validation;
	
	private final PasswordEncoder passwordEncoder;
	
	public RegisterPage(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@GetMapping
	public String showRegisterPage() {
		return "register";
	}
	
	@PostMapping
	public String registerNewUser(@RequestParam String username, @RequestParam  String password, RedirectAttributes attributes) {
		List<String> errors = validation.validateRegistrationInput(username);
		if(errors.isEmpty()) {
			String encodedPW = passwordEncoder.encode(password);
			userServ.registerUser(username, encodedPW);
			return "redirect:/login";
		}
		attributes.addFlashAttribute("errors",errors);
		return "redirect:/register";
		
	}
}
