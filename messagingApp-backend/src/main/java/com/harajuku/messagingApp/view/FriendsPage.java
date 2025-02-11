package com.harajuku.messagingApp.view;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.harajuku.messagingApp.controller.FriendshipController;
import com.harajuku.messagingApp.controller.UserController;
import com.harajuku.messagingApp.model.User;
import com.harajuku.messagingApp.service.FriendshipService;
import com.harajuku.messagingApp.service.UserService;

@Controller
public class FriendsPage {

	@Autowired
	UserService userServ;

	@Autowired
	FriendshipService friendServ;

	@GetMapping("friends/{userId}")
	public String showFriendsPage(@PathVariable long userId, Model model) {
		User user = userServ.findById(userId);
		List<User> friends = user.getFriends();
		model.addAttribute("user", user);
		model.addAttribute("friends", friends);
		List<User> optionalFriends = friendServ.findOptionalFriends(user, friends);
		model.addAttribute("optionalFriends", optionalFriends);
		return "friends";
	}

	@PostMapping("/friends/add")
	public String addAFriend(@RequestParam("friendId") long friendId, Principal prince) {
		User friend = userServ.findById(friendId);
		User loggedInUser = userServ.findUserByPrincipal(prince);
		friendServ.saveUnilateralFriendship(loggedInUser, friend);
		return "redirect:/friends/" + loggedInUser.getId();
	}

}
