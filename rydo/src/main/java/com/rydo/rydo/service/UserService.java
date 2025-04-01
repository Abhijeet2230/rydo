package com.rydo.rydo.service;

import com.rydo.rydo.DTO.UserRegistrationDTO;
import com.rydo.rydo.entity.User;

public interface UserService {

	 User registerUser(UserRegistrationDTO userDTO);
	 
//	LoginResponse userLogin(LoginRequest loginRequest);

	
	 
}
