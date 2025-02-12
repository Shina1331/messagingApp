package com.harajuku.messagingApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harajuku.messagingApp.model.FriendRequest;
import com.harajuku.messagingApp.model.User;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long>{

	List<FriendRequest> findByReceiver(User receiver);

}
