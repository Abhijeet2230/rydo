package com.rydo.rydo.entity;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "drivers", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email"),
    @UniqueConstraint(columnNames = "phone"),
    @UniqueConstraint(columnNames = "licenseNumber")
})
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driverId;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Column(nullable = false, unique = true)
    private String phone;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "License number is required")
    @Column(nullable = false, unique = true)
    private String licenseNumber;

    private String currentLocation;

    private double rating = 0.0;

    private boolean availabilityStatus = true;
    
    private String role ;

    // One Driver has One Vehicle
    @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Vehicle vehicle;

    // One Driver can have multiple Rides
    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ride> rides;
}
