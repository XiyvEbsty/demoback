package com.example.service.impl;

import com.example.entity.HollandQuestion;
import com.example.mapper.HollandQuestionMapper;
import com.example.service.HollandQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HollandQuestionServiceImpl implements HollandQuestionService {

    @Autowired
    private HollandQuestionMapper hollandQuestionMapper;

    @Override
    public List<HollandQuestion> getAllQuestions() {
        return hollandQuestionMapper.findAll();
    }

    @Override
    public HollandQuestion getQuestionById(Long id) {
        return hollandQuestionMapper.findById(id);
    }

    @Override
    public List<HollandQuestion> getQuestionsByType(String type) {
        return hollandQuestionMapper.findByType(type);
    }

    @Override
    public boolean addQuestion(HollandQuestion question) {
        return hollandQuestionMapper.insert(question) > 0;
    }

    @Override
    public boolean updateQuestion(HollandQuestion question) {
        return hollandQuestionMapper.update(question) > 0;
    }

    @Override
    public boolean deleteQuestion(Long id) {
        return hollandQuestionMapper.deleteById(id) > 0;
    }
} 