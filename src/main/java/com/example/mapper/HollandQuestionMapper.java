package com.example.mapper;

import com.example.entity.HollandQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HollandQuestionMapper {

    List<HollandQuestion> findAll();
    
    HollandQuestion findById(@Param("id") Long id);
    
    List<HollandQuestion> findByType(@Param("type") String type);
    
    int insert(HollandQuestion question);
    
    int update(HollandQuestion question);
    
    int deleteById(@Param("id") Long id);
} 