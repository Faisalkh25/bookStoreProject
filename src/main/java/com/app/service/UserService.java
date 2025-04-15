package com.app.service;

import java.util.List;

import com.app.model.User;

public interface UserService {

	  public User saveUser(User user);
	  public User getUserById(int id);
	  public List<User> showAllUsers();
	  public User authenticateUser(String email, String password);
}
