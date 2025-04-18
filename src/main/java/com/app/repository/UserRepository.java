package com.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	   public User findByEmailAndPassword(String email, String password);
	   Optional<User> findByEmail(String email);
}
