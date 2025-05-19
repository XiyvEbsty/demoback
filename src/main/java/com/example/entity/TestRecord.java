package com.example.entity;

import java.time.LocalDateTime;

public class TestRecord {
    private Long id;
    private Long userId;
    private String testType; // "holland" 或 "career_anchor"
    
    // Holland 职业兴趣分数
    private Integer realisticScore;
    private Integer investigativeScore;
    private Integer artisticScore;
    private Integer socialScore;
    private Integer enterprisingScore;
    private Integer conventionalScore;
    
    // 职业锚分数
    private Integer technicalFunctionalScore;
    private Integer generalManagerialScore;
    private Integer autonomyIndependenceScore;
    private Integer securityStabilityScore;
    private Integer entrepreneurialCreativityScore;
    private Integer serviceDedicationScore;
    private Integer pureChallengeScore;
    private Integer lifestyleScore;
    
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public Integer getRealisticScore() {
        return realisticScore;
    }

    public void setRealisticScore(Integer realisticScore) {
        this.realisticScore = realisticScore;
    }

    public Integer getInvestigativeScore() {
        return investigativeScore;
    }

    public void setInvestigativeScore(Integer investigativeScore) {
        this.investigativeScore = investigativeScore;
    }

    public Integer getArtisticScore() {
        return artisticScore;
    }

    public void setArtisticScore(Integer artisticScore) {
        this.artisticScore = artisticScore;
    }

    public Integer getSocialScore() {
        return socialScore;
    }

    public void setSocialScore(Integer socialScore) {
        this.socialScore = socialScore;
    }

    public Integer getEnterprisingScore() {
        return enterprisingScore;
    }

    public void setEnterprisingScore(Integer enterprisingScore) {
        this.enterprisingScore = enterprisingScore;
    }

    public Integer getConventionalScore() {
        return conventionalScore;
    }

    public void setConventionalScore(Integer conventionalScore) {
        this.conventionalScore = conventionalScore;
    }

    public Integer getTechnicalFunctionalScore() {
        return technicalFunctionalScore;
    }

    public void setTechnicalFunctionalScore(Integer technicalFunctionalScore) {
        this.technicalFunctionalScore = technicalFunctionalScore;
    }

    public Integer getGeneralManagerialScore() {
        return generalManagerialScore;
    }

    public void setGeneralManagerialScore(Integer generalManagerialScore) {
        this.generalManagerialScore = generalManagerialScore;
    }

    public Integer getAutonomyIndependenceScore() {
        return autonomyIndependenceScore;
    }

    public void setAutonomyIndependenceScore(Integer autonomyIndependenceScore) {
        this.autonomyIndependenceScore = autonomyIndependenceScore;
    }

    public Integer getSecurityStabilityScore() {
        return securityStabilityScore;
    }

    public void setSecurityStabilityScore(Integer securityStabilityScore) {
        this.securityStabilityScore = securityStabilityScore;
    }

    public Integer getEntrepreneurialCreativityScore() {
        return entrepreneurialCreativityScore;
    }

    public void setEntrepreneurialCreativityScore(Integer entrepreneurialCreativityScore) {
        this.entrepreneurialCreativityScore = entrepreneurialCreativityScore;
    }

    public Integer getServiceDedicationScore() {
        return serviceDedicationScore;
    }

    public void setServiceDedicationScore(Integer serviceDedicationScore) {
        this.serviceDedicationScore = serviceDedicationScore;
    }

    public Integer getPureChallengeScore() {
        return pureChallengeScore;
    }

    public void setPureChallengeScore(Integer pureChallengeScore) {
        this.pureChallengeScore = pureChallengeScore;
    }

    public Integer getLifestyleScore() {
        return lifestyleScore;
    }

    public void setLifestyleScore(Integer lifestyleScore) {
        this.lifestyleScore = lifestyleScore;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
} 