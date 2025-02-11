package com.harajuku.messagingApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harajuku.messagingApp.model.ChatMessage;
import com.harajuku.messagingApp.model.ChatRoom;
import com.harajuku.messagingApp.model.SimpleTextMessage;
@Repository
public interface MessageRepository extends JpaRepository<ChatMessage, String>{

	void save(SimpleTextMessage message);
	List<ChatMessage> findAllByChatRoom(ChatRoom room);
	void deleteAllByChatRoom(ChatRoom room);
}
