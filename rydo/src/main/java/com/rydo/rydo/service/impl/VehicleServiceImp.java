package com.rydo.rydo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rydo.rydo.entity.Vehicle;
import com.rydo.rydo.repository.VehicleRepository;
import com.rydo.rydo.service.VehicleService;

@Service
public class VehicleServiceImp implements VehicleService {

	@Autowired
	private VehicleRepository vehicleRepository;

	@Override
	public void registerVehicle(Vehicle vehicle) {

		vehicleRepository.save(vehicle);

	}

}
