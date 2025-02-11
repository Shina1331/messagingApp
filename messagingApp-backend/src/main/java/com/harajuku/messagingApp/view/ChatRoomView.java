package com.harajuku.messagingApp.view;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.harajuku.messagingApp.model.ChatMessage;
import com.harajuku.messagingApp.model.ChatRoom;
import com.harajuku.messagingApp.model.SimpleTextMessage;
import com.harajuku.messagingApp.model.User;
import com.harajuku.messagingApp.service.ChatRoomService;
import com.harajuku.messagingApp.service.MessageService;
import com.harajuku.messagingApp.service.UserService;

@Controller
public class ChatRoomView<T> {

	@Autowired
	UserService userServ;

	@Autowired
	ChatRoomService chatServ;

	@Autowired
	MessageService messageServ;

	@Autowired
	SimpMessagingTemplate messagingTemplate;

	@GetMapping("/chatRoom/{chatId}")
	public String showPrivateChatRoom(@PathVariable long chatId, Principal prince, Model model) {
		User user = userServ.findUserByPrincipal(prince);
		ChatRoom chat = chatServ.findById(chatId);
		if (chat.getParticipants().contains(user)) {
			List<ChatMessage> previousMessages = messageServ.getPreviousMessagesToDisplay(chat);
			model.addAttribute("previousMessages", previousMessages);
			model.addAttribute("user", user);
			model.addAttribute("chat", chat);
		} else {
			return "/landingPage/" + user.getId();
		}
		return "/chatRoom";
	}

	
	@MessageMapping("/chatRoom/{chatId}/chat.send")
	@SendTo("/topic/chatRoom/{chatId}/messages")
	public SimpleTextMessage sendMessage(@DestinationVariable long chatId, SimpleTextMessage simp, Principal prince) {
		User sender = userServ.findUserByPrincipal(prince);
		simp.setSender(sender);
		simp.setChatRoom(chatServ.findById(chatId));
		messageServ.saveSimpleMessage(simp);
		return simp;
	}
}
