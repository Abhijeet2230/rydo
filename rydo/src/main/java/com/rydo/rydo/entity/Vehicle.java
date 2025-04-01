package com.rydo.rydo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import jakarta.persistence.Column;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vehicles", uniqueConstraints = {
    @UniqueConstraint(columnNames = "vehicleNumber") // Ensuring vehicle number is unique
})
public class Vehicle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;

    @OneToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "driverId", nullable = false, unique = true)
    private Driver driver;

    @NotBlank(message = "Vehicle number is required")
    @Column(nullable = false, unique = true)
    private String vehicleNumber;

    @NotBlank(message = "Vehicle type is required")
    private String vehicleType;

    private int capacity;

    private boolean status = true; // Default to available
}
