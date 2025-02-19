package com.harajuku.messagingApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.harajuku.messagingApp.enums.AdminMessages;
import com.harajuku.messagingApp.model.User;
import com.harajuku.messagingApp.service.InputValidation;
import com.harajuku.messagingApp.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginPage {

	@Autowired
	UserService userServ;

//	@Autowired
//	InputValidation validation;

	public LoginPage() {

	}

	@GetMapping
	public String showLoginPage(@RequestParam(value = "logout", required = false) String logout,
			RedirectAttributes redirectAttributes) {
		if (logout != null) {
			redirectAttributes.addFlashAttribute("logoutMessage", AdminMessages.LOGOUT_SUCCESS);
			return "redirect:/login";
		}
		return "login";
	}

	@PostMapping
	public String login(@RequestParam String username, @RequestParam String password, Model model) {
		User user = userServ.findByUsername(username);
		long userId = user.getId();

		model.addAttribute("user", user);

		return "redirect:/landingPage/" + userId;
	}

}
