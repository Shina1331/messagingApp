package com.harajuku.messagingApp.enums;

public enum UserToUser {

	FRIEND_REQUEST_PENDING("Pending"),
	FRIEND_REQUEST_ACCEPTED("Accepted");
	
	private String message;
	
	private UserToUser(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
