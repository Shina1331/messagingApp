package com.harajuku.messagingApp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harajuku.messagingApp.model.ChatMessage;
import com.harajuku.messagingApp.model.ChatRoom;
import com.harajuku.messagingApp.model.SimpleTextMessage;
import com.harajuku.messagingApp.repository.MessageRepository;

@Service
@Transactional
public class MessageService {

	@Autowired
	MessageRepository messageRep;
	
	public void saveSimpleMessage(SimpleTextMessage message){
		messageRep.save(message);
	}
	
	public List<ChatMessage> getTextMessagesByChatRoom(ChatRoom room){
		//List<ChatMessage> allMessages = messageRep.findAll();
		
		
		return messageRep.findAllByChatRoom(room); 
	}
	public List<ChatMessage> getPreviousMessagesToDisplay(ChatRoom chat) {
		List<ChatMessage> allMessagesOfRoom = getTextMessagesByChatRoom(chat);
		return getFiveLatest(allMessagesOfRoom);
	}
	
	public List<ChatMessage> getFiveLatest(List<ChatMessage> messages) {
		List<ChatMessage> res = new ArrayList<>();
		int count = 0;
		for (int i = messages.size() - 1; i >= 0; i--) {
			if (count < 5) {
				res.add(messages.get(i));
				count++;
			} else {
				break;
			}
		}
		Collections.reverse(res);
		return res;
	}
	@Transactional
	public void deleteAllMessagesByChat(ChatRoom room){
		messageRep.deleteAllByChatRoom(room);
	}
}
