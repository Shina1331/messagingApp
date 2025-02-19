package com.harajuku.messagingApp.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		model.addAttribute("pendingRequests", friendServ.getAllPendingFriendRequests(userId));
		return "friends";
	}

	@PostMapping("/friends/sendFriendRequest")
	public String sendRequest(@RequestParam("friendId") long friendId, Principal prince) {
		User loggedInUser = userServ.findUserByPrincipal(prince);
		friendServ.sendFriendRequest(loggedInUser.getId(), friendId);
		return "redirect:/friends/" + loggedInUser.getId();
	}

	@PostMapping("/friends/acceptFriendRequest")
	public String acceptRequest(@RequestParam("senderId") long senderId, @RequestParam("requestId") long requestId,
			Principal prince, RedirectAttributes redirectAttr) {
		User loggedInUser = userServ.findUserByPrincipal(prince);
		friendServ.acceptFriendRequest(loggedInUser.getId(), senderId, requestId);
		redirectAttr.addFlashAttribute("pendingRequests", friendServ.getAllPendingFriendRequests(loggedInUser.getId()));
		redirectAttr.addFlashAttribute("friends", loggedInUser.getFriends());
		return "redirect:/friends/" + loggedInUser.getId();
	}

}
