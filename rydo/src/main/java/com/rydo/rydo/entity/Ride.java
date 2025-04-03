package com.rydo.rydo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rides")
public class Ride {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rideId;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "userId")
	private User user;

	@ManyToOne
	@JoinColumn(name = "driver_id", referencedColumnName = "driverId")
	private Driver driver;

	@NotBlank
	private String pickupLocation;

	@NotBlank
	private String dropLocation;

	private double fareEstimate;

	private double fareFinal;
	
	@NotBlank
	private String vehicleType;  


    @Enumerated(EnumType.STRING)
    private RideStatus status;  // Pending, Accepted, In Progress, Completed, Cancelled

	private String otp;

	private LocalDateTime startTime;

	private LocalDateTime endTime;
}
