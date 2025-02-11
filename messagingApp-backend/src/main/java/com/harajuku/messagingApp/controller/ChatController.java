package com.harajuku.messagingApp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import com.harajuku.messagingApp.model.ChatMessage;
import com.harajuku.messagingApp.model.ChatRoom;
import com.harajuku.messagingApp.model.GroupChat;
import com.harajuku.messagingApp.model.PrivateChat;
import com.harajuku.messagingApp.model.SimpleTextMessage;
import com.harajuku.messagingApp.model.User;
import com.harajuku.messagingApp.service.ChatRoomService;
import com.harajuku.messagingApp.service.MessageService;
import com.harajuku.messagingApp.service.UserService;

@Controller
public class ChatController {

	@Autowired
	UserService userServ;

	@Autowired
	ChatRoomService chatServ;

	@Autowired
	MessageService messageServ;

	public ChatController() {
	}

//	public ChatRoom findById(long id) {
//		return chatServ.findById(id);
//	}

}