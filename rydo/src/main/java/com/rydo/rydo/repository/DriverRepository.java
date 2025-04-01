package com.rydo.rydo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rydo.rydo.entity.Driver;

public interface DriverRepository extends JpaRepository<Driver, Long> {

	boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByLicenseNumber(String licenseNumber);
	Optional<Driver> findByEmail(String email);
	Optional<Driver> findByPhone(String phone);
}
