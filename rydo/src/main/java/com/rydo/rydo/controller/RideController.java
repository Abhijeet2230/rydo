package com.rydo.rydo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.rydo.rydo.entity.Ride;
import com.rydo.rydo.entity.RideStatus;
import com.rydo.rydo.service.RideService;

import java.util.Map;

@Controller
@RequestMapping("/user/ride")
public class RideController {

    @Autowired
    private RideService rideService;

    // Show the page to book a new ride
    @GetMapping("/book")
    public String showRideBookingForm(Model model) {
        model.addAttribute("ride", new Ride());
        return "book-ride";  // This should match the booking page template
    }

    // Handle booking a ride (POST request)
    @PostMapping("/book")
    public String bookRide(@ModelAttribute("ride") Ride ride, Model model) {
        // Calculate fare estimate
        Map<String, Double> fareEstimate = rideService.calculateFare(ride.getPickupLocation(), ride.getDropLocation());
        model.addAttribute("fareEstimate", fareEstimate);
        
        // Save ride details
        rideService.createRide(ride);
        model.addAttribute("message", "Ride booked successfully!");
        return "ride-summary";  // Show the ride summary page
    }

    // Get fare estimate before booking a ride
    @GetMapping("/fare")
    @ResponseBody
    public Map<String, Double> getFareEstimate(@RequestParam String pickup, @RequestParam String drop) {
        return rideService.calculateFare(pickup, drop);
    }

    // Show ride details
    @GetMapping("/{rideId}")
    public String viewRide(@PathVariable Long rideId, Model model) {
        Ride ride = rideService.getRide(rideId);
        if (ride != null) {
            model.addAttribute("ride", ride);
        } else {
            model.addAttribute("error", "Ride not found");
        }
        return "ride-details";  // Show the ride details page
    }

    // Update the status of the ride (e.g., when a driver accepts a ride)
    @PutMapping("/{rideId}/status")
    public String updateRideStatus(@PathVariable Long rideId, @RequestParam("status") RideStatus status, Model model) {
        rideService.updateRideStatus(rideId, status);
        model.addAttribute("message", "Ride status updated!");
        return "ride-status";  // Show the updated ride status page
    }
}
