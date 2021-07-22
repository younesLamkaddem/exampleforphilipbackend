package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/users")
public class UserController {

	
	@Autowired
	PasswordEncoder encoder;
	
	
	
	@Autowired
	private UserRepository userRep;
	
	@GetMapping
	public List<User> getUsers(){
		return this.userRep.findAll();
	}
	
	/*
	@PutMapping("/{email}")
	User updateUser(@PathVariable String email) {
		User findedUser = userRep.findByUsername(email)
			     .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + email));
		findedUser.setPassword(encoder.encode("siham"));
		//String password = 
		
		
		
		return this.userRep.saveAndFlush(findedUser);
	}
	*/

	
	@GetMapping("/{email}")
	User updateUser(@PathVariable String email) {
		User findedUser = userRep.findByEmail(email)
			     .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + email));
		//findedUser.setPassword(encoder.encode("siham"));
		//String password = 
		//String password = encoder.encode(findedUser.getPassword());
		
		return findedUser;
		//return this.userRep.saveAndFlush(findedUser);
	}
	
	
}
