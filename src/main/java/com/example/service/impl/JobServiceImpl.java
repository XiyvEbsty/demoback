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
        
        // Holland code weight (50% of total score)
        double hollandWeight = 0.5;
        
        // Career anchor weight (50% of total score)
        double anchorWeight = 0.5;
        
        // 直接使用MyBatis XML中定义的复杂查询方法
        return jobMapper.findJobsByHollandAndAnchorScores(
            realistic, 
            investigative, 
            artistic, 
            social, 
            enterprising, 
            conventional,
            technicalFunctional,
            generalManagerial,
            autonomyIndependence,
            securityStability,
            entrepreneurialCreativity,
            serviceDedication,
            pureChallenge,
            lifestyle,
            hollandWeight,
            anchorWeight,
            limit != null ? limit : 3
        );
    }
} 