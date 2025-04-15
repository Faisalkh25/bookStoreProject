package com.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.User;
import com.app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	 @Autowired
	 private UserRepository userRepo;
	
	@Override
	public User saveUser(User user) {
		        User u = userRepo.save(user);
		return u;
	}

	@Override
	public User getUserById(int id) {
		         User user = userRepo.findById(id).get();
		return user;
	}

	@Override
	public List<User> showAllUsers() {
		           List<User> users = userRepo.findAll();
		return users;
	}

	@Override
	public User authenticateUser(String email, String password) {
		         User us = userRepo.findByEmail(email).filter(user -> user.getPassword().equals(password)).orElse(null);
		return us;
	}

}
