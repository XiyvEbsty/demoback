package com.example.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.util.Map;

@Data
@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    
    private String company;
    
    private String description;
    
    private String industry;
    
    private String type;
    
    private String location;
    
    private String jobUrl;
    
    @Column(name = "realistic_score")
    private Integer realisticScore;
    
    @Column(name = "investigative_score")
    private Integer investigativeScore;
    
    @Column(name = "artistic_score")
    private Integer artisticScore;
    
    @Column(name = "social_score")
    private Integer socialScore;
    
    @Column(name = "enterprising_score")
    private Integer enterprisingScore;
    
    @Column(name = "conventional_score")
    private Integer conventionalScore;
    
    @Column(name = "technical_functional_score")
    private Integer technicalFunctionalScore;
    
    @Column(name = "general_managerial_score")
    private Integer generalManagerialScore;
    
    @Column(name = "autonomy_independence_score")
    private Integer autonomyIndependenceScore;
    
    @Column(name = "security_stability_score")
    private Integer securityStabilityScore;
    
    @Column(name = "entrepreneurial_creativity_score")
    private Integer entrepreneurialCreativityScore;
    
    @Column(name = "service_dedication_score")
    private Integer serviceDedicationScore;
    
    @Column(name = "pure_challenge_score")
    private Integer pureChallengeScore;
    
    @Column(name = "lifestyle_score")
    private Integer lifestyleScore;
} 