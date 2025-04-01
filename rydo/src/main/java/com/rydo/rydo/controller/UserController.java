package com.rydo.rydo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.rydo.rydo.DTO.UserRegistrationDTO;
import com.rydo.rydo.entity.User;
import com.rydo.rydo.service.UserService;
import org.springframework.security.core.Authentication;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	// Registration form (GET request)
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new UserRegistrationDTO());
		return "register"; // Show registration page
	}

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDTO userDTO, 
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register"; // Stay on registration page if errors
        }

        // Check if user registration is successful
        User registeredUser = userService.registerUser(userDTO);
        if (registeredUser == null) {
            model.addAttribute("error", "Email is already registered!");
            return "register"; // Stay on registration page with error
        }

        // Redirect to login page after successful registration
        return "redirect:/login?success=registered";
    }

    @GetMapping("/home")
    public String userHome(Model model, Authentication authentication) {
        if (authentication == null || !authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"))) {
            return "redirect:/login";
        }
        return "home";
    }

}
