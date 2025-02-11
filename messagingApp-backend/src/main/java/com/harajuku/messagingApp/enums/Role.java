package com.harajuku.messagingApp.enums;

public enum Role {

	STANDARD_USER("Standard"),
	MODERATOR_USER("Moderator"),
	ADMIN_USER("Admin");

	private String message;
	
	Role( String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
