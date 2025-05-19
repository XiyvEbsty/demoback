package com.example.service;

import com.example.entity.HollandQuestion;

import java.util.List;

public interface HollandQuestionService {

    List<HollandQuestion> getAllQuestions();
    
    HollandQuestion getQuestionById(Long id);
    
    List<HollandQuestion> getQuestionsByType(String type);
    
    boolean addQuestion(HollandQuestion question);
    
    boolean updateQuestion(HollandQuestion question);
    
    boolean deleteQuestion(Long id);
} 