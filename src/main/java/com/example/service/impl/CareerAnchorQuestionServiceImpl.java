package com.example.service.impl;

import com.example.entity.CareerAnchorQuestion;
import com.example.mapper.CareerAnchorQuestionMapper;
import com.example.service.CareerAnchorQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerAnchorQuestionServiceImpl implements CareerAnchorQuestionService {

    @Autowired
    private CareerAnchorQuestionMapper careerAnchorQuestionMapper;

    @Override
    public List<CareerAnchorQuestion> getAllQuestions() {
        return careerAnchorQuestionMapper.findAll();
    }

    @Override
    public CareerAnchorQuestion getQuestionById(Long id) {
        return careerAnchorQuestionMapper.findById(id);
    }

    @Override
    public List<CareerAnchorQuestion> getQuestionsByType(String type) {
        return careerAnchorQuestionMapper.findByType(type);
    }

    @Override
    public boolean addQuestion(CareerAnchorQuestion question) {
        return careerAnchorQuestionMapper.insert(question) > 0;
    }

    @Override
    public boolean updateQuestion(CareerAnchorQuestion question) {
        return careerAnchorQuestionMapper.update(question) > 0;
    }

    @Override
    public boolean deleteQuestion(Long id) {
        return careerAnchorQuestionMapper.deleteById(id) > 0;
    }
} 