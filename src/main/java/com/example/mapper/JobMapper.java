package com.example.mapper;

import com.example.entity.Job;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JobMapper {

    List<Job> findAll();

    Job findById(@Param("id") Long id);

    int insert(Job job);

    int update(Job job);

    int deleteById(@Param("id") Long id);
    
    List<Job> findMatchingJobs(@Param("industry") String industry, 
                              @Param("type") String type, 
                              @Param("location") String location);
                              
    /**
     * 根据Holland职业兴趣和Career Anchor职业锚分数查找最匹配的职位
     */
    List<Job> findJobsByHollandAndAnchorScores(
        @Param("realistic") Integer realistic,
        @Param("investigative") Integer investigative,
        @Param("artistic") Integer artistic,
        @Param("social") Integer social,
        @Param("enterprising") Integer enterprising,
        @Param("conventional") Integer conventional,
        @Param("technicalFunctional") Integer technicalFunctional,
        @Param("generalManagerial") Integer generalManagerial,
        @Param("autonomyIndependence") Integer autonomyIndependence,
        @Param("securityStability") Integer securityStability,
        @Param("entrepreneurialCreativity") Integer entrepreneurialCreativity,
        @Param("serviceDedication") Integer serviceDedication,
        @Param("pureChallenge") Integer pureChallenge,
        @Param("lifestyle") Integer lifestyle,
        @Param("hollandWeight") Double hollandWeight,
        @Param("anchorWeight") Double anchorWeight,
        @Param("limit") Integer limit
    );
} 