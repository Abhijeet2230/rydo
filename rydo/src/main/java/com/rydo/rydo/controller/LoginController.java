package com.rydo.rydo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {


	// Show login page (GET request)
	@GetMapping("/login")
	public String showLoginPage(Model model) {
//		model.addAttribute("loginRequest", new LoginRequest()); // Add a blank LoginRequest object to the model
		return "login"; // Return the login page (login.html)
	}
}
