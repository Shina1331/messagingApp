package com.harajuku.messagingApp.enums;

public enum AdminMessages {

	LOGOUT_SUCCESS("You have been successfully logged out."),
	GROUP_CREATION_FAILED("Please select at least one member for the Group Chat");

	private String message;

	AdminMessages(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
