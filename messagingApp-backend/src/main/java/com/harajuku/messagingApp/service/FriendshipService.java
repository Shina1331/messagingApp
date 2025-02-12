package com.harajuku.messagingApp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harajuku.messagingApp.enums.UserToUser;
import com.harajuku.messagingApp.model.FriendRequest;
import com.harajuku.messagingApp.model.User;
import com.harajuku.messagingApp.repository.FriendRequestRepository;
@Service
public class FriendshipService {

	@Autowired
	UserService userServ;
	
	@Autowired
	FriendRequestRepository friendRep;

	
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

	public void sendFriendRequest(long userId, long friendId) {
		User user = userServ.findById(userId);
		User receiver = userServ.findById(friendId);
		FriendRequest request = new FriendRequest();
		request.setSender(user);
		request.setReceiver(receiver);
		request.setStatus(UserToUser.FRIEND_REQUEST_PENDING.getMessage());
		friendRep.save(request);
		//receiver.addRequest(request);
	}
	
	public List<FriendRequest> getAllPendingFriendRequests(long userId){
		List<FriendRequest> all = friendRep.findByReceiver(userServ.findById(userId));
		List<FriendRequest> res = new ArrayList<>();
		for(int i = 0; i< all.size(); i++) {
			if(all.get(i).getStatus().equals(UserToUser.FRIEND_REQUEST_PENDING.getMessage())){
				res.add(all.get(i));
			}
		}
		return res;
	}

	public void acceptFriendRequest(long userId, long senderId, long requestId) {
		User receiver = userServ.findById(userId);
		User sender = userServ.findById(senderId);
		receiver.addFriend(sender);
		sender.addFriend(receiver);
		FriendRequest request = friendRep.findById(requestId).get();
		request.setStatus(UserToUser.FRIEND_REQUEST_ACCEPTED.getMessage());
		friendRep.save(request);
		userServ.save(sender);
		userServ.save(receiver);
	}
}
