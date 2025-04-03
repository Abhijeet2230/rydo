package com.rydo.rydo.websocket.config;


import com.rydo.rydo.entity.Ride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RideNotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void notifyDrivers(Ride ride) {
        messagingTemplate.convertAndSend("/topic/rideRequests", ride);
    }
}
