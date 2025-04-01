package com.rydo.rydo.controller;

import com.rydo.rydo.DTO.DriverRegistrationDTO;
import com.rydo.rydo.entity.Driver;
import com.rydo.rydo.entity.Vehicle;
import com.rydo.rydo.service.DriverService;
import com.rydo.rydo.service.VehicleService;
import java.security.Principal;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/driver")
public class DriverController {

	@Autowired
	private DriverService driverService;
	
	@Autowired
	private VehicleService vehicleService;

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("driver", new DriverRegistrationDTO()); // Ensure model contains 'driver'
		return "registerdriver";
	}

	@PostMapping("/register")
	public String registerDriver(@ModelAttribute("driver") DriverRegistrationDTO driverDTO, Model model) {
		try {
			driverService.registerDriver(driverDTO);
			return "redirect:/login"; // Redirect to login page after successful registration
		} catch (IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("driver", driverDTO); // Keep user input if there's an error
			return "registerdriver"; // Stay on registration page if there's an error
		}
	}
	
	@GetMapping("/register-vehicle")
	public String showVehicleRegistration(Model model) {
		model.addAttribute("vehicle", new Vehicle());
		return "vehicle_registration";
		
	}
	
	@PostMapping("/register-vehicle")
	public String registerVehicle(@ModelAttribute Vehicle vehicle, Principal principal, RedirectAttributes redirectAttributes) {
	    // Fetch the logged-in driver's details
	    String email = principal.getName();
	    Optional<Driver> optionalDriver = driverService.findByEmail(email);

	    if (optionalDriver.isEmpty()) {
	        redirectAttributes.addFlashAttribute("error", "Driver not found.");
	        return "redirect:/driver/home";
	    }

	    Driver driver = optionalDriver.get();  // Extract Driver from Optional

	    // Check if the driver has already registered a vehicle
	    if (driver.getVehicle() != null) {
	        redirectAttributes.addFlashAttribute("error", "You have already registered a vehicle.");
	        return "redirect:/driver/home";
	    }

	    // Assign the driver to the vehicle and save
	    vehicle.setDriver(driver);
	    vehicleService.registerVehicle(vehicle);

	    redirectAttributes.addFlashAttribute("success", "Vehicle registered successfully!");
	    return "redirect:/driver/home";
	}
	
	 @GetMapping("/home")
	    public String driverHome(Model model, Authentication authentication) {
	        if (authentication == null || !authentication.getAuthorities().stream()
	                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_DRIVER"))) {
	            return "redirect:/login";
	        }
	        return "homedriver";
	    }


}
