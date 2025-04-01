package com.rydo.rydo.service;

import java.util.Map;

import com.rydo.rydo.entity.Ride;
import com.rydo.rydo.entity.RideStatus;

public interface RideService {
	
	Ride createRide(Ride ride);
    Ride getRide(Long rideId);
    
    void updateRideStatus(Long rideId, RideStatus status);

    // Add this method to the interface
    Map<String, Double> calculateFare(String pickup, String drop);
	
}
