package com.harajuku.messagingApp.model;

import java.util.List;

public class UserManager <T extends User>{

	UserManager um;
	List<T> users;

	private UserManager() {
	};

	public UserManager getInstance() {
		if (um == null) {
			return new UserManager();
		} else {
			return um;
		}
	}
}
