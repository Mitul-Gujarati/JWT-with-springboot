package com.jwt.example.Controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.example.Services.UserService;
import com.jwt.example.models.User;

@RestController
@RequestMapping("/home")
public class HomeController {

	@Autowired
	private UserService userService;

	@GetMapping("/user")
	public List<User> getUser() {
		List<User> users = userService.getUsers();
		return users;
	}
	
	@GetMapping("/current-user")
	public String getCurrentUser(Principal principal) { 
		return principal.getName();
	}


}
