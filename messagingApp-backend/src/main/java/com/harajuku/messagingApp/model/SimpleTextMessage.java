package com.harajuku.messagingApp.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "messages")
@DiscriminatorValue("SimpleTextMessage")
public class SimpleTextMessage extends ChatMessage{
	@Column(name = "text")
	private String text;
	
	public SimpleTextMessage() {}
	
	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setText(String text) {
		this.text = text;
	}
}
