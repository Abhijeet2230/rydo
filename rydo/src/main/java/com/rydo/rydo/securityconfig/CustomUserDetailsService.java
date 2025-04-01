package com.rydo.rydo.securityconfig;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//import org.springframework.security.core.userdetails.User; // Import this

import com.rydo.rydo.entity.Driver;
import com.rydo.rydo.entity.User;
import com.rydo.rydo.repository.DriverRepository;
import com.rydo.rydo.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DriverRepository driverRepository;

    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Inside loadUserByUsername: " + username);
        
        // First, check if the username belongs to a user
        Optional<User> userOpt = userRepository.findByEmail(username)
                .or(() -> userRepository.findByPhone(username));
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            System.out.println("User found: " + user.getEmail());
            return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole())  // Ensure user.getRole() does not start with "ROLE_"
                    .build();
        }

        // If not a user, check if it's a driver
        Optional<Driver> driverOpt = driverRepository.findByEmail(username)
                .or(() -> driverRepository.findByPhone(username));

        if (driverOpt.isPresent()) {
            Driver driver = driverOpt.get();
            System.out.println("Driver found: " + driver.getEmail());
            return org.springframework.security.core.userdetails.User.withUsername(driver.getEmail())
                    .password(driver.getPassword())
                    .roles(driver.getRole())  // Ensure driver.getRole() does not start with "ROLE_"
                    .build();
        }

        System.out.println("User or Driver not found for: " + username);
        throw new UsernameNotFoundException("User or Driver not found");
    }

}
