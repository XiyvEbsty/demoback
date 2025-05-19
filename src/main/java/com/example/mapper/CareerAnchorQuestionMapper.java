package com.example.mapper;

import com.example.entity.CareerAnchorQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CareerAnchorQuestionMapper {

    List<CareerAnchorQuestion> findAll();
    
    CareerAnchorQuestion findById(@Param("id") Long id);
    
    List<CareerAnchorQuestion> findByType(@Param("type") String type);
    
    int insert(CareerAnchorQuestion question);
    
    int update(CareerAnchorQuestion question);
    
    int deleteById(@Param("id") Long id);
} 