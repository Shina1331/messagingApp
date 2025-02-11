package com.harajuku.messagingApp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harajuku.messagingApp.model.ChatMessage;
import com.harajuku.messagingApp.model.ChatRoom;
import com.harajuku.messagingApp.model.GroupChat;
import com.harajuku.messagingApp.model.PrivateChat;
import com.harajuku.messagingApp.model.SimpleTextMessage;
import com.harajuku.messagingApp.model.User;
import com.harajuku.messagingApp.repository.ChatRoomRepository;

@Service
public class ChatRoomService {

	@Autowired
	ChatRoomRepository chatRep;
	
	@Autowired
	UserService userServ;
	
	@Autowired
	MessageService messageServ;

	public ChatRoom<?> save(ChatRoom<?> room) {
		return chatRep.save(room);
	}

	public ChatRoom findById(long chatId) {
		return chatRep.findById(chatId);
	}


	public List<User> findAllFriendsWithoutPrivateChat(List<User> friends, User user) {
		List<User> res = new ArrayList<>();
		for (int i = 0; i < friends.size(); i++) {
			User curr = friends.get(i);
			if (!hasPrivateChatBetweenUserandFriend(user, curr)) {
				res.add(curr);
			}
		}
		return res;
	}
	
	public Boolean hasPrivateChatBetweenUserandFriend(User user, User friend) {
		List<ChatRoom> rooms = user.getChatRooms();
		for (int i = 0; i < rooms.size(); i++) {
			ChatRoom<ChatMessage> curr = rooms.get(i);
			if (friend.getChatRooms().contains(curr) && curr instanceof PrivateChat) {
				return true;
			}
		}
		return false;

	}
	
	public long createNewGroupChat(long userId, List<User> addedFriendsList) {
		ChatRoom chat = new GroupChat();
		User user = userServ.findById(userId);
		chat.addParticipant(user);
		for(int i = 0; i< addedFriendsList.size(); i++) {
			chat.addParticipant(addedFriendsList.get(i));
		}
		long chatId = save(chat).getId();
		updateUsersChatRoom(user, addedFriendsList, chat);
		return chatId;
	}
	
	public long createNewPrivateChat(long userId, long friendId) {
		ChatRoom chat = new PrivateChat();
		User user = userServ.findById(userId);
		User friend = userServ.findById(friendId);
		chat.addParticipant(user);
		chat.addParticipant(friend);
//		chat.setMessages(new ArrayList<ChatMessage>());
		long chatId = save(chat).getId();
		List<User> friends = new ArrayList<User>();
		friends.add(friend);
		updateUsersChatRoom(user, friends, chat);
		return chatId;
	}
	
	public void updateUsersChatRoom(User user, List<User> friends, ChatRoom chat) {
		user.addChatRoom(chat);
		userServ.save(user);
		for(User friend : friends) {
			friend.addChatRoom(chat);
			userServ.save(friend);
		}
	}
	@Transactional
	public void deleteChat(long chatId) {
		ChatRoom room = findById(chatId);
		messageServ.deleteAllMessagesByChat(room);
		 List<User> users = new ArrayList<>(room.getParticipants()); 
		for(User user : users) {
			room.removeParticipant(user);
			user.getChatRooms().remove(room);
			userServ.save(user);
		}
		save(room);
		chatRep.delete(room);
	}
	
	
}
