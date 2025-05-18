package com.example.controller;

import com.example.common.Result;
import com.example.dto.JobMatchRequest;
import com.example.entity.Job;
import com.example.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping
    public Result<List<Job>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();
        return Result.success(jobs);
    }

    @GetMapping("/{id}")
    public Result<Job> getJobById(@PathVariable Long id) {
        Job job = jobService.getJobById(id);
        if (job != null) {
            return Result.success(job);
        } else {
            return Result.error("404", "Job not found");
        }
    }

    @PostMapping
    public Result<Boolean> addJob(@RequestBody Job job) {
        boolean success = jobService.addJob(job);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("500", "Failed to add job");
        }
    }

    @PutMapping("/{id}")
    public Result<Boolean> updateJob(@PathVariable Long id, @RequestBody Job job) {
        job.setId(id);
        boolean success = jobService.updateJob(job);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("500", "Failed to update job");
        }
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> deleteJob(@PathVariable Long id) {
        boolean success = jobService.deleteJob(id);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("500", "Failed to delete job");
        }
    }

    @PostMapping("/match")
    public Result<List<Job>> getMatchingJobs(@RequestBody JobMatchRequest request) {
        List<Job> matchingJobs = jobService.getMatchingJobs(
                request.getRealistic(),
                request.getInvestigative(),
                request.getArtistic(),
                request.getSocial(),
                request.getEnterprising(),
                request.getConventional(),
                request.getTechnicalFunctional(),
                request.getGeneralManagerial(),
                request.getAutonomyIndependence(),
                request.getSecurityStability(),
                request.getEntrepreneurialCreativity(),
                request.getServiceDedication(),
                request.getPureChallenge(),
                request.getLifestyle(),
                request.getLimit() != null ? request.getLimit() : 3
        );
        return Result.success(matchingJobs);
    }
} 