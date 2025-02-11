package com.harajuku.messagingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harajuku.messagingApp.model.ChatRoom;
import com.harajuku.messagingApp.model.User;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {

	public abstract ChatRoom findById(long chatId);

}
