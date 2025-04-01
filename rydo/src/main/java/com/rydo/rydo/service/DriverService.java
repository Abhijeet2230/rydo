package com.rydo.rydo.service;

import java.util.Optional;

import com.rydo.rydo.DTO.DriverRegistrationDTO;
import com.rydo.rydo.DTO.LoginRequest;
import com.rydo.rydo.DTO.LoginResponse;
import com.rydo.rydo.entity.Driver;

public interface DriverService {

	Driver registerDriver(DriverRegistrationDTO driverDTO);
	
	LoginResponse driverLogin(LoginRequest loginRequest);

	Optional<Driver> findByEmail(String username);
}
