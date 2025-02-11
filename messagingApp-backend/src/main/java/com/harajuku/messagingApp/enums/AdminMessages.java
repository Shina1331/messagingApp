package com.harajuku.messagingApp.enums;

public enum AdminMessages {

	LOGOUT_SUCCESS("You have been successfully logged out.");

	private String message;

	AdminMessages(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
