package com.harajuku.messagingApp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javax.persistence.InheritanceType;
import javax.persistence.Inheritance;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "dtype")
@JsonSubTypes({
    @JsonSubTypes.Type(value = PrivateChat.class, name = "PrivateChat"),
    @JsonSubTypes.Type(value = GroupChat.class, name = "GroupChat"),
    // Add other subclasses here if you have more
})
@Table(name = "ChatRooms")
public abstract class ChatRoom<T extends ChatMessage> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	@Column(name = "Name")
	String name;
//	@OneToMany(targetEntity = ChatMessage.class, mappedBy = "chatRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<ChatMessage> messages;

	@ManyToMany
	@JoinTable(name = "chatroom_participants", joinColumns = @JoinColumn(name = "chatroom_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	List<User> participants;

	public ChatRoom() {
//		this.messages = new ArrayList<ChatMessage>();
		this.participants = new ArrayList<User>();
	}
	
	public void addParticipant(User user) {
		participants.add(user);
	}

	public List<User> getParticipants() {
		return participants;
	}

	public void removeParticipant(User user) {
		participants.remove(user);
	}
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean isGroupChat(ChatRoom chat) {
		return chat instanceof GroupChat;
	}

//	public List<ChatMessage> getMessages() {
//		return messages;
//	}
//
//	public void setMessages(List<ChatMessage> messages) {
//		this.messages = messages;
//	}
//
//	public void addMessage(SimpleTextMessage message) {
//		messages.add(message);
//	}
//	public void setId(long id2) {
//		this.id = id2;
//		
//	}
}
