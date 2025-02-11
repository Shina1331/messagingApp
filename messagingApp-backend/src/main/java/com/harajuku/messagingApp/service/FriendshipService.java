package com.harajuku.messagingApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harajuku.messagingApp.model.User;
@Service
public class FriendshipService {

	@Autowired
	UserService userServ;

	
	public List<User> findOptionalFriends(User currentUser, List<User> friends) {
		List<User> allUsers = userServ.findAll();
		allUsers.remove(currentUser);
		allUsers.removeAll(friends);
		return allUsers;
	}
	
	public void saveUnilateralFriendship(User loggedInUser, User friend) {
		loggedInUser.addFriend(friend);
		userServ.save(loggedInUser);
			}
}
