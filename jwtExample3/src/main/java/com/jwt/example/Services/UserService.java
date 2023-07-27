package com.jwt.example.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jwt.example.models.User;

@Service
public class UserService {
	
	private List<User> store = new ArrayList<>();
	
	public UserService() {
		store.add(new User(UUID.randomUUID().toString(), "Mitul Gujarati", "mitul@gmail.com"));
		store.add(new User(UUID.randomUUID().toString(), "Bhushan Varpe", "Bhushan@gmail.com"));
		store.add(new User(UUID.randomUUID().toString(), "Jenil Savani", "Jenil@gmail.com"));
		store.add(new User(UUID.randomUUID().toString(), "Dhruvit Kukadiya", "Dhruvit@gmail.com"));
		store.add(new User(UUID.randomUUID().toString(), "Akshay Jain", "Akshay@gmail.com"));
	}
	
	public List<User> getUsers()
	{
		return store;
	}

}
