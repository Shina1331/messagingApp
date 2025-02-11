package com.harajuku.messagingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harajuku.messagingApp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

	User findByUsername(String name);

	User findById(long userId);

}
