package com.harajuku.messagingApp.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("GroupChat")
public class GroupChat extends ChatRoom{

	
	public GroupChat() {
		super();
	}
}
