package com.rydo.rydo.service.impl;

import com.rydo.rydo.DTO.DriverRegistrationDTO;
import com.rydo.rydo.DTO.LoginRequest;
import com.rydo.rydo.DTO.LoginResponse;
import com.rydo.rydo.entity.Driver;
import com.rydo.rydo.repository.DriverRepository;
import com.rydo.rydo.service.DriverService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverRepository driverRepository;


	@Autowired
	private PasswordEncoder passwordEncoder;

    

    @Override
    public Driver registerDriver(DriverRegistrationDTO driverRegistrationDTO) {
        if (driverRepository.existsByEmail(driverRegistrationDTO.getEmail())) {
            throw new IllegalArgumentException("Email is already in use!");
        }
        if (driverRepository.existsByPhone(driverRegistrationDTO.getPhone())) {
            throw new IllegalArgumentException("Phone number is already in use!");
        }
        if (driverRepository.existsByLicenseNumber(driverRegistrationDTO.getLicenseNumber())) {
            throw new IllegalArgumentException("License number is already in use!");
        }

        Driver driver = new Driver();
        driver.setName(driverRegistrationDTO.getName());
        driver.setEmail(driverRegistrationDTO.getEmail());
        driver.setPhone(driverRegistrationDTO.getPhone());
        driver.setPassword(passwordEncoder.encode(driverRegistrationDTO.getPassword())); // Encode password
        driver.setLicenseNumber(driverRegistrationDTO.getLicenseNumber());
        driver.setCurrentLocation(driverRegistrationDTO.getCurrentLocation());
        driver.setRole("DRIVER");

        return driverRepository.save(driver);
    }
    
    

    public LoginResponse driverLogin(LoginRequest loginRequest) {
        // Retrieve driver from database
        Optional<Driver> optionalDriver = driverRepository.findByEmail(loginRequest.getEmail());

        // If driver is not found
        if (optionalDriver.isEmpty()) {
            return new LoginResponse("Invalid email or password", false, null);
        }

        Driver driver = optionalDriver.get();

        // Check password match
        if (!loginRequest.getPassword().equals(driver.getPassword())) {
            return new LoginResponse("Invalid email or password", false, null);
        }

        // Convert driver to UserDetails (needed for Spring Security)
        UserDetails driverDetails = new org.springframework.security.core.userdetails.User(
            driver.getEmail(),
            driver.getPassword(),
            List.of(new SimpleGrantedAuthority(driver.getRole())) // Assign role
        );

        // Return successful login with driver details
        return new LoginResponse(driver.getRole(), true, driverDetails);
    }

    public Optional<Driver> findByEmail(String username) {
    	return driverRepository.findByEmail(username);
    }
    
}
