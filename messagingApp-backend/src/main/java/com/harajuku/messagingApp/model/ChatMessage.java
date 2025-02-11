package com.harajuku.messagingApp.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "dtype")
@JsonSubTypes({
    @JsonSubTypes.Type(value = SimpleTextMessage.class, name = "SimpleTextMessage"),
    // Add other subclasses here if you have more
})
@Table(name = "Messages")
public abstract class ChatMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	@ManyToOne
	@JoinColumn(name = "sender")
	private User sender;

	@ManyToOne
	@JoinColumn(name = "chatRoom")
	private ChatRoom chatRoom;

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public abstract void setText(String message);

	public abstract  String getText() ;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ChatRoom getChatRoom() {
		return chatRoom;
	}

	public void setChatRoom(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
	}
	

}
