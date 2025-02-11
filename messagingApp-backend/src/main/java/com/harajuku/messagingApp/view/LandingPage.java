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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.harajuku.messagingApp.enums.AdminMessages;
import com.harajuku.messagingApp.model.User;
import com.harajuku.messagingApp.service.ChatRoomService;
import com.harajuku.messagingApp.service.UserService;

@Controller
public class LandingPage {

	@Autowired
	UserService userServ;

	@Autowired
	ChatRoomService chatServ;

	@GetMapping("/landingPage/{userId}")
	public String showLandingPage(@PathVariable long userId, Model model) {
		User user = userServ.findById(userId);
		model.addAttribute("user", user);
		model.addAttribute("activeChats", user.getChatRooms());
		model.addAttribute("friends", user.getFriends());
		List<User> friendsWithoutPriorChat = chatServ.findAllFriendsWithoutPrivateChat(user.getFriends(), user);
		model.addAttribute("friendsWithoutPriorChat", friendsWithoutPriorChat);
		return "/landingPage";
	}

	@PostMapping("/startNewChat")
	public String startNewChat(@RequestParam long friendId, Principal prince) {
		long userId = userServ.findUserByPrincipal(prince).getId();
		long chatId = chatServ.createNewPrivateChat(userId, friendId);
		return "redirect:/chatRoom/" + chatId;
	}

	@PostMapping("/startNewGroupChat")
	public String startGroupChat(@RequestParam("addedFriends") String addedFriends, Principal prince, RedirectAttributes redirectAttributes) {
		long userId = userServ.findUserByPrincipal(prince).getId();
		List<User> listOfFriends = userServ.generateListOfUsers(addedFriends);
		if (listOfFriends.size() > 0) {
			long chatId = chatServ.createNewGroupChat(userId, listOfFriends);
			return "redirect:/chatRoom/" + chatId;
		} else {
			redirectAttributes.addFlashAttribute("errorGroupChatCreation", AdminMessages.GROUP_CREATION_FAILED);
			return "redirect:/landingPage/" + userId;
		}

	}

	@PostMapping("/deleteChat")
	public String deleteChat(@RequestParam("chatId") long chatId, Principal prince, Model model) {
		User user = userServ.findByUsername(prince.getName());
		long userId = user.getId();
		chatServ.deleteChat(chatId);
		model.addAttribute("activeChats", user.getChatRooms());
		return "redirect:/landingPage/" + userId;
	}

}
