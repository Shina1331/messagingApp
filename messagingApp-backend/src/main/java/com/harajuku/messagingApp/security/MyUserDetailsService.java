package com.harajuku.messagingApp.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.harajuku.messagingApp.model.User;
import com.harajuku.messagingApp.repository.UserRepository;

public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	UserRepository userRep;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	User user =	userRep.findByUsername(username);
		  if (user == null) {
	            throw new UsernameNotFoundException("User not found");
	        }

	        return new org.springframework.security.core.userdetails.User(
	                user.getUsername(),
	                user.getPassword(), 
          Arrays.asList(new SimpleGrantedAuthority(user.getRole()))); // Assuming `role` is an Enum
	        
	    }
	}


