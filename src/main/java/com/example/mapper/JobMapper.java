package com.example.mapper;

import com.example.entity.Job;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JobMapper {

    @Select("SELECT * FROM jobs")
    List<Job> findAll();

    @Select("SELECT * FROM jobs WHERE id = #{id}")
    Job findById(@Param("id") Long id);

    @Insert("INSERT INTO jobs(title, company, description, industry, type, location, jobUrl, " +
            "realistic_score, investigative_score, artistic_score, social_score, enterprising_score, conventional_score, " +
            "technical_functional_score, general_managerial_score, autonomy_independence_score, security_stability_score, " +
            "entrepreneurial_creativity_score, service_dedication_score, pure_challenge_score, lifestyle_score) " +
            "VALUES(#{title}, #{company}, #{description}, #{industry}, #{type}, #{location}, #{jobUrl}, " +
            "#{realisticScore}, #{investigativeScore}, #{artisticScore}, #{socialScore}, #{enterprisingScore}, #{conventionalScore}, " +
            "#{technicalFunctionalScore}, #{generalManagerialScore}, #{autonomyIndependenceScore}, #{securityStabilityScore}, " +
            "#{entrepreneurialCreativityScore}, #{serviceDedicationScore}, #{pureChallengeScore}, #{lifestyleScore})")
    int insert(Job job);

    @Update("UPDATE jobs SET title = #{title}, company = #{company}, description = #{description}, " +
            "industry = #{industry}, type = #{type}, location = #{location}, jobUrl = #{jobUrl}, " +
            "realistic_score = #{realisticScore}, investigative_score = #{investigativeScore}, " +
            "artistic_score = #{artisticScore}, social_score = #{socialScore}, " +
            "enterprising_score = #{enterprisingScore}, conventional_score = #{conventionalScore}, " +
            "technical_functional_score = #{technicalFunctionalScore}, general_managerial_score = #{generalManagerialScore}, " +
            "autonomy_independence_score = #{autonomyIndependenceScore}, security_stability_score = #{securityStabilityScore}, " +
            "entrepreneurial_creativity_score = #{entrepreneurialCreativityScore}, service_dedication_score = #{serviceDedicationScore}, " +
            "pure_challenge_score = #{pureChallengeScore}, lifestyle_score = #{lifestyleScore} " +
            "WHERE id = #{id}")
    int update(Job job);

    @Delete("DELETE FROM jobs WHERE id = #{id}")
    int deleteById(@Param("id") Long id);
} 