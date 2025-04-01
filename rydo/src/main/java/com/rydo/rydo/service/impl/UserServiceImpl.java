package com.rydo.rydo.service.impl;

import com.rydo.rydo.DTO.UserRegistrationDTO;
import com.rydo.rydo.entity.User;
import com.rydo.rydo.repository.UserRepository;
import com.rydo.rydo.service.UserService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User registerUser(UserRegistrationDTO userRegistrationDTO) {
		if (userRepository.findByEmail(userRegistrationDTO.getEmail()).isPresent()) {
			return null; // Email already exists
		}

		User user = new User();
		user.setName(userRegistrationDTO.getName());
		user.setEmail(userRegistrationDTO.getEmail());
		user.setPhone(userRegistrationDTO.getPhone());
		user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword())); // Encode password
		user.setLocation(userRegistrationDTO.getLocation());
		user.setRole("USER");
		user.setCreatedAt(LocalDateTime.now());

		return userRepository.save(user);
	}


}
