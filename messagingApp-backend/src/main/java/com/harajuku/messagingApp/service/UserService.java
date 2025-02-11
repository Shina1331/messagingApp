package com.harajuku.messagingApp.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harajuku.messagingApp.enums.Role;
import com.harajuku.messagingApp.model.User;
import com.harajuku.messagingApp.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRep;

	@Autowired
	public UserService(UserRepository userRep) {
		this.userRep = userRep;
	}

	public void registerUser(String username, String password) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setRole(Role.STANDARD_USER.getMessage());
		user.setChatRooms(new ArrayList<>());
		userRep.save(user);
	}

	public List<User> findAll() {
		return userRep.findAll();
	}

	public User findByUsername(String username) {
		return userRep.findByUsername(username);
	}

	public User findById(long userId) {
		return userRep.findById(userId);
	}

	public void save(User user) {
		userRep.save(user);
	}

	public User findUserByPrincipal(Principal prince) {
		return findByUsername(prince.getName());
	}

	public List<User> generateListOfUsers(String addedFriends) {
		List<User> users = new ArrayList<>();
		if (!addedFriends.isBlank()) {
			addedFriends.split(",");
			for (int i = 0; i < addedFriends.split(",").length; i++) {
				users.add(findById(Integer.valueOf(addedFriends.split(",")[i])));
			}
		}
		return users;
	}
}
