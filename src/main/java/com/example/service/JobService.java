package com.example.service;

import com.example.entity.Job;
import java.util.List;

public interface JobService {
    
    List<Job> getAllJobs();
    
    Job getJobById(Long id);
    
    boolean addJob(Job job);
    
    boolean updateJob(Job job);
    
    boolean deleteJob(Long id);
    
    /**
     * Get best matching jobs based on Holland code and Career Anchor scores
     * @param hollandScores Map of Holland code dimensions and their scores
     * @param anchorScores Map of Career Anchor dimensions and their scores
     * @param limit Number of top matches to return
     * @return List of best matching jobs
     */
    List<Job> getMatchingJobs(
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
        Integer limit
    );
} 