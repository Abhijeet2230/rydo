package com.rydo.rydo.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DriverRegistrationDTO {

	@NotBlank(message = "Name is required")
	private String name;

	@Email(message = "Invalid email format")
	@NotBlank(message = "Email is required")
	private String email;

	@NotBlank(message = "Phone number is required")
	@Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
	private String phone;

	@NotBlank(message = "Password is required")
	@Size(min = 6, message = "Password must be at least 6 characters")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$", message = "Password must be at least 6 characters, contain at least one uppercase letter, one number, and one special character, and must not contain spaces")
	private String password;

	@NotBlank(message = "License number is required")
	@Pattern(regexp = "^[A-Z0-9-]+$", message = "Invalid license number format")
	private String licenseNumber;

	@NotBlank(message = "Vehicle ID is required")
	@Pattern(regexp = "^[A-Z]{2}[- ]?\\d{1,2}[- ]?[A-Z]{1,2}[- ]?\\d{4}$", message = "Invalid vehicle ID format (e.g., MH12AB1234 or MH-12-AB-1234)")
	private String vehicleId;

	@NotBlank(message = "Current location is required")
	@Pattern(regexp = "^[a-zA-Z0-9\\s,-]+$", message = "Location can only contain letters, numbers, spaces, hyphens, and commas")
	private String currentLocation;

	private String role = "ROLE_DRIVER"; // Default role
}
