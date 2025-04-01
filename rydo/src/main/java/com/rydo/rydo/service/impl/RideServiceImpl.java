package com.rydo.rydo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import com.rydo.rydo.entity.Ride;
import com.rydo.rydo.entity.RideStatus;
import com.rydo.rydo.repository.RideRepository;
import com.rydo.rydo.service.RideService;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class RideServiceImpl implements RideService {

    @Autowired
    private RideRepository rideRepository;

    @Value("${google.maps.api.key}")
    private String googleApiKey;

    @Value("${fare.auto}")
    private double autoRate;

    @Value("${fare.cab}")
    private double cabRate;

    private static final Logger logger = LoggerFactory.getLogger(RideServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Ride createRide(Ride ride) {
        return rideRepository.save(ride);
    }

    @Override
    public Ride getRide(Long rideId) {
        return rideRepository.findById(rideId).orElse(null);
    }

    @Transactional
    @Override
    public void updateRideStatus(Long rideId, RideStatus status) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));
        ride.setStatus(status);
        rideRepository.save(ride);
    }

    @Override
    public Map<String, Double> calculateFare(String pickup, String drop) {
        double distance = getDistanceInKm(pickup, drop);
        if (distance <= 0) {
            throw new IllegalArgumentException("Invalid distance calculated: " + distance);
        }

        double autoFare = distance * autoRate;
        double cabFare = distance * cabRate;

        if (autoFare <= 0 || cabFare <= 0) {
            throw new IllegalArgumentException("Invalid fare calculated for auto or cab: " +
               "Auto Fare = " + autoFare + ", Cab Fare = " + cabFare);
        }

        return Map.of(
            "autoFare", autoFare,
            "cabFare", cabFare
        );
    }

    public double getDistanceInKm(String pickup, String drop) {
        logger.info("Fetching distance for pickup: {} and drop: {}", pickup, drop);
        
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + 
                     pickup + "&destinations=" + drop + "&key=" + googleApiKey;

        try {
            // Fetch API response
            Map<String, Object> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {}
            ).getBody();

            // Log the API response for debugging
            logger.info("Google Maps API Response: {}", response);

            // Validate response structure
            if (response == null || !response.containsKey("rows")) {
                throw new RuntimeException("Google API response does not contain 'rows'");
            }

            List<?> rows = (List<?>) response.get("rows");
            if (rows.isEmpty() || !(rows.get(0) instanceof Map<?, ?> firstRow)) {
                throw new RuntimeException("Invalid response format in 'rows'");
            }

            List<?> elements = (List<?>) firstRow.get("elements");
            if (elements.isEmpty() || !(elements.get(0) instanceof Map<?, ?> firstElement)) {
                throw new RuntimeException("Invalid response format in 'elements'");
            }

            Map<?, ?> distanceMap = (Map<?, ?>) firstElement.get("distance");
            if (distanceMap == null || !distanceMap.containsKey("value")) {
                throw new RuntimeException("Invalid response format for 'distance'");
            }

            Object valueObj = distanceMap.get("value");
            if (!(valueObj instanceof Number value)) {
                throw new RuntimeException("Invalid 'distance' value format");
            }

            double distanceInKm = value.doubleValue() / 1000.0; // Convert meters to km
            logger.info("Calculated distance: {} km", distanceInKm);

            return distanceInKm;

        } catch (Exception e) {
            logger.error("Error processing Google API response: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch distance from Google API", e);
        }
    }

}
