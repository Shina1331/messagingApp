package com.harajuku.messagingApp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "Username")
	private String username;
	@Column(name = "Password")
	private String password;
	@ManyToMany(mappedBy = "participants")
	@JsonIgnore
	private List<ChatRoom> chatRooms;
	@Column(name = "Role")
	private String role;
	@ManyToMany
	@JsonIgnore
	@JoinTable(name = "friendships", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
	private List<User> friends;
//	@JsonIgnore
//	@OneToMany(targetEntity = FriendRequest.class, mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<FriendRequest> receivedRequests;

	public User() {
		this.chatRooms = new ArrayList<ChatRoom>();
		this.friends = new ArrayList<User>();
//		this.receivedRequests = new ArrayList<FriendRequest>();
	}

//	public void addRequest(FriendRequest request) {
//		receivedRequests.add(request);
//	}
//	
//	public void removeRequest(FriendRequest request) {
//		receivedRequests.remove(request);
//	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<ChatRoom> getChatRooms() {
		return chatRooms;
	}

	public void setChatRooms(List<ChatRoom> chatRooms) {
		this.chatRooms = chatRooms;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String string) {
		this.role = string;
	}

	public List<User> getFriends() {
		return friends;
	}

	public void addFriend(User friend) {
		friends.add(friend);
	}

	public void addChatRoom(ChatRoom room) {
		chatRooms.add(room);
	}

	public Boolean isFriend(User person) {
		return friends.contains(person);

	}
}
