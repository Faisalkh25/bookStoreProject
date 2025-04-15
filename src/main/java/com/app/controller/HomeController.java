package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.model.Role;
import com.app.model.User;
import com.app.repository.RoleRepository;
import com.app.repository.UserRepository;
import com.app.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	  
	   private UserService userService;
	   private RoleRepository roleRepo;
	   	
	   public HomeController(UserService userService, RoleRepository roleRepo) {
		super();
		this.userService = userService;
		this.roleRepo = roleRepo;
	}

	@GetMapping("/")
	   public String showHome() {
		   
		     return "index";
	   }
	   
	   @GetMapping("/registerForm")
	   public String showRegistrationForm() {
		   
		   return "registration";
	   }
	   
	   @GetMapping("/loginForm")
	   public String showLoginForm() {
		    
		   return "login";
	   }
	   
	   @PostMapping("/register")
	   public String registerUser(User user, Model model) {
		       Role userRole = roleRepo.findById(2).orElseThrow(() -> new RuntimeException("role not found: "));
		    
		            user.setRole(userRole);
		            userService.saveUser(user);
		   return "redirect:/loginForm";
	   }
	   
	   @GetMapping("/login")
	   public String loginUser(@RequestParam String email, @RequestParam String password, HttpSession session, Model model ) {
		           User authenticatedUser = userService.authenticateUser(email, password);
		           
		           if(authenticatedUser != null) {
		        	        session.setAttribute("user", authenticatedUser);
		        	        
		        	        if(authenticatedUser.getRole().getId() == 1) {
		        	        	return "redirect:/admin-panel";
		        	        }
		        	        else {
		        	        	return "redirect:/user-panel";
		        	        }
		           }
		           else {
		        	   model.addAttribute("error", "user not found");
		        	   return "loginForm";
		           }
	   }
	   
	   @GetMapping("/logout")
	   public String logoutUser(HttpSession session) {
		      session.invalidate();
		      
		      return "redirect:/loginForm";
	   }
}
