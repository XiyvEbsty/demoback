package com.example.service;

import com.example.entity.CareerAnchorQuestion;

import java.util.List;

public interface CareerAnchorQuestionService {

    List<CareerAnchorQuestion> getAllQuestions();
    
    CareerAnchorQuestion getQuestionById(Long id);
    
    List<CareerAnchorQuestion> getQuestionsByType(String type);
    
    boolean addQuestion(CareerAnchorQuestion question);
    
    boolean updateQuestion(CareerAnchorQuestion question);
    
    boolean deleteQuestion(Long id);
} 