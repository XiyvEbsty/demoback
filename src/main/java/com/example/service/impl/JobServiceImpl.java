package com.example.service.impl;

import com.example.entity.Job;
import com.example.mapper.JobMapper;
import com.example.service.JobService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobMapper jobMapper;

    @Override
    public List<Job> getAllJobs() {
        return jobMapper.findAll();
    }

    @Override
    public Job getJobById(Long id) {
        return jobMapper.findById(id);
    }

    @Override
    public boolean addJob(Job job) {
        return jobMapper.insert(job) > 0;
    }

    @Override
    public boolean updateJob(Job job) {
        return jobMapper.update(job) > 0;
    }

    @Override
    public boolean deleteJob(Long id) {
        return jobMapper.deleteById(id) > 0;
    }

    @Override
    public List<Job> getMatchingJobs(
            Integer realistic,
            Integer investigative,
            Integer artistic,
            Integer social,
            Integer enterprising,
            Integer conventional,
            Integer technicalFunctional,
            Integer generalManagerial,
            Integer autonomyIndependence,
            Integer securityStability,
            Integer entrepreneurialCreativity,
            Integer serviceDedication,
            Integer pureChallenge,
            Integer lifestyle,
            Integer limit) {
        
        // Get all jobs
        List<Job> allJobs = jobMapper.findAll();
        
        // Calculate match score for each job
        Map<Job, Double> jobScores = new HashMap<>();
        
        // Holland code weight (50% of total score)
        double hollandWeight = 0.5;
        
        // Career anchor weight (50% of total score)
        double anchorWeight = 0.5;
        
        for (Job job : allJobs) {
            // Calculate Holland code match (dot product)
            double hollandMatch = calculateHollandMatch(
                job, realistic, investigative, artistic, social, enterprising, conventional);
            
            // Calculate Career Anchor match
            double anchorMatch = calculateAnchorMatch(
                job, technicalFunctional, generalManagerial, autonomyIndependence, 
                securityStability, entrepreneurialCreativity, serviceDedication, 
                pureChallenge, lifestyle);
            
            // Calculate final weighted score
            double finalScore = (hollandMatch * hollandWeight) + (anchorMatch * anchorWeight);
            
            // Store the score
            jobScores.put(job, finalScore);
        }
        
        // Sort jobs by score (descending) and get top 'limit' results
        return jobScores.entrySet().stream()
                .sorted(Map.Entry.<Job, Double>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
    
    /**
     * Calculate Holland code match score using dot product and normalization
     */
    private double calculateHollandMatch(
            Job job, 
            Integer realistic, 
            Integer investigative, 
            Integer artistic, 
            Integer social, 
            Integer enterprising, 
            Integer conventional) {
        
        // Calculate dot product
        double dotProduct = 0.0;
        dotProduct += job.getRealisticScore() * realistic;
        dotProduct += job.getInvestigativeScore() * investigative;
        dotProduct += job.getArtisticScore() * artistic;
        dotProduct += job.getSocialScore() * social;
        dotProduct += job.getEnterprisingScore() * enterprising;
        dotProduct += job.getConventionalScore() * conventional;
        
        // Calculate magnitudes
        double jobMagnitude = Math.sqrt(
                Math.pow(job.getRealisticScore(), 2) +
                Math.pow(job.getInvestigativeScore(), 2) +
                Math.pow(job.getArtisticScore(), 2) +
                Math.pow(job.getSocialScore(), 2) +
                Math.pow(job.getEnterprisingScore(), 2) +
                Math.pow(job.getConventionalScore(), 2));
        
        double userMagnitude = Math.sqrt(
                Math.pow(realistic, 2) +
                Math.pow(investigative, 2) +
                Math.pow(artistic, 2) +
                Math.pow(social, 2) +
                Math.pow(enterprising, 2) +
                Math.pow(conventional, 2));
        
        // Return cosine similarity (normalized dot product)
        if (jobMagnitude == 0 || userMagnitude == 0) {
            return 0.0;
        }
        
        return dotProduct / (jobMagnitude * userMagnitude);
    }
    
    /**
     * Calculate Career Anchor match score using dot product and normalization
     */
    private double calculateAnchorMatch(
            Job job,
            Integer technicalFunctional,
            Integer generalManagerial,
            Integer autonomyIndependence,
            Integer securityStability,
            Integer entrepreneurialCreativity,
            Integer serviceDedication,
            Integer pureChallenge,
            Integer lifestyle) {
        
        // Calculate dot product
        double dotProduct = 0.0;
        dotProduct += job.getTechnicalFunctionalScore() * technicalFunctional;
        dotProduct += job.getGeneralManagerialScore() * generalManagerial;
        dotProduct += job.getAutonomyIndependenceScore() * autonomyIndependence;
        dotProduct += job.getSecurityStabilityScore() * securityStability;
        dotProduct += job.getEntrepreneurialCreativityScore() * entrepreneurialCreativity;
        dotProduct += job.getServiceDedicationScore() * serviceDedication;
        dotProduct += job.getPureChallengeScore() * pureChallenge;
        dotProduct += job.getLifestyleScore() * lifestyle;
        
        // Calculate magnitudes
        double jobMagnitude = Math.sqrt(
                Math.pow(job.getTechnicalFunctionalScore(), 2) +
                Math.pow(job.getGeneralManagerialScore(), 2) +
                Math.pow(job.getAutonomyIndependenceScore(), 2) +
                Math.pow(job.getSecurityStabilityScore(), 2) +
                Math.pow(job.getEntrepreneurialCreativityScore(), 2) +
                Math.pow(job.getServiceDedicationScore(), 2) +
                Math.pow(job.getPureChallengeScore(), 2) +
                Math.pow(job.getLifestyleScore(), 2));
        
        double userMagnitude = Math.sqrt(
                Math.pow(technicalFunctional, 2) +
                Math.pow(generalManagerial, 2) +
                Math.pow(autonomyIndependence, 2) +
                Math.pow(securityStability, 2) +
                Math.pow(entrepreneurialCreativity, 2) +
                Math.pow(serviceDedication, 2) +
                Math.pow(pureChallenge, 2) +
                Math.pow(lifestyle, 2));
        
        // Return cosine similarity (normalized dot product)
        if (jobMagnitude == 0 || userMagnitude == 0) {
            return 0.0;
        }
        
        return dotProduct / (jobMagnitude * userMagnitude);
    }
} 