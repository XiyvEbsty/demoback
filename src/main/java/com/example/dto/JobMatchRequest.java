package com.example.dto;

import lombok.Data;

@Data
public class JobMatchRequest {
    
    // Holland code dimensions
    private Integer realistic;
    private Integer investigative;
    private Integer artistic;
    private Integer social;
    private Integer enterprising;
    private Integer conventional;
    
    // Career anchor dimensions
    private Integer technicalFunctional;
    private Integer generalManagerial;
    private Integer autonomyIndependence;
    private Integer securityStability;
    private Integer entrepreneurialCreativity;
    private Integer serviceDedication;
    private Integer pureChallenge;
    private Integer lifestyle;
    
    // Number of results to return
    private Integer limit;
} 