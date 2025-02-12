package com.harajuku.messagingApp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Friend_Requests")
public class FriendRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String status;
//	@ManyToMany
//	@JoinTable(name = "friendRequests", 
//	joinColumns = @JoinColumn(name = "sender_id"), 
//	inverseJoinColumns = @JoinColumn(name = "status")
//	)
	@ManyToOne
	@JoinColumn(name = "sender")
	private User sender;

	@ManyToOne
	@JoinColumn(name = "receiver")
	private User receiver;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User initiator) {
		this.sender = initiator;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public long getId() {
		return id;
	}

}
