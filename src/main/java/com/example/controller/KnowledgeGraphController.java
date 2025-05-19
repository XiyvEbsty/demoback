package com.example.controller;

import com.example.service.KnowledgeGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/knowledge-graph")
public class KnowledgeGraphController {

    private final KnowledgeGraphService knowledgeGraphService;

    @Autowired
    public KnowledgeGraphController(KnowledgeGraphService knowledgeGraphService) {
        this.knowledgeGraphService = knowledgeGraphService;
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        Map<String, Object> status = knowledgeGraphService.checkStatus();
        return ResponseEntity.ok(status);
    }

    @PostMapping("/initialize")
    public ResponseEntity<String> initializeKnowledgeGraph() {
        knowledgeGraphService.initializeKnowledgeGraph();
        return ResponseEntity.ok("Knowledge graph initialized successfully");
    }

    @PostMapping("/import-jobs")
    public ResponseEntity<String> importJobsFromJsData(@RequestBody List<Map<String, Object>> jobsData) {
        knowledgeGraphService.importJobsFromJsData(jobsData);
        return ResponseEntity.ok("Jobs imported to knowledge graph successfully");
    }

    @PostMapping("/recommend/holland")
    public ResponseEntity<List<Map<String, Object>>> getRecommendedJobsByHolland(
            @RequestBody Map<String, Integer> hollandScores) {
        return ResponseEntity.ok(knowledgeGraphService.getRecommendedJobsByHollandProfile(hollandScores));
    }

    @PostMapping("/recommend/anchor")
    public ResponseEntity<List<Map<String, Object>>> getRecommendedJobsByAnchor(
            @RequestBody Map<String, Integer> anchorScores) {
        return ResponseEntity.ok(knowledgeGraphService.getRecommendedJobsByAnchorProfile(anchorScores));
    }

    @PostMapping("/recommend/combined")
    public ResponseEntity<List<Map<String, Object>>> getRecommendedJobsByCombined(
            @RequestBody Map<String, Map<String, Integer>> combinedScores) {
        return ResponseEntity.ok(knowledgeGraphService.getRecommendedJobsByCombinedProfile(
                combinedScores.get("hollandScores"), 
                combinedScores.get("anchorScores")));
    }
} 