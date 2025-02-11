package com.harajuku.messagingApp.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("PrivateChat")
public class PrivateChat extends ChatRoom<ChatMessage> {

	public PrivateChat() {
		super();
	}
}
