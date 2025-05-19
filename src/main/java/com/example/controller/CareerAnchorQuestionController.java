package com.example.controller;

import com.example.common.Result;
import com.example.entity.CareerAnchorQuestion;
import com.example.service.CareerAnchorQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/career-anchor-questions")
@CrossOrigin
public class CareerAnchorQuestionController {

    @Autowired
    private CareerAnchorQuestionService careerAnchorQuestionService;

    @GetMapping
    public Result<List<CareerAnchorQuestion>> getAllQuestions() {
        List<CareerAnchorQuestion> questions = careerAnchorQuestionService.getAllQuestions();
        return Result.success(questions);
    }

    @GetMapping("/{id}")
    public Result<CareerAnchorQuestion> getQuestionById(@PathVariable Long id) {
        CareerAnchorQuestion question = careerAnchorQuestionService.getQuestionById(id);
        if (question != null) {
            return Result.success(question);
        } else {
            return Result.error("404", "问题不存在");
        }
    }

    @GetMapping("/type/{type}")
    public Result<List<CareerAnchorQuestion>> getQuestionsByType(@PathVariable String type) {
        List<CareerAnchorQuestion> questions = careerAnchorQuestionService.getQuestionsByType(type);
        return Result.success(questions);
    }

    @PostMapping
    public Result<Boolean> addQuestion(@RequestBody CareerAnchorQuestion question) {
        boolean success = careerAnchorQuestionService.addQuestion(question);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("500", "添加失败");
        }
    }

    @PutMapping("/{id}")
    public Result<Boolean> updateQuestion(@PathVariable Long id, @RequestBody CareerAnchorQuestion question) {
        question.setId(id);
        boolean success = careerAnchorQuestionService.updateQuestion(question);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("500", "更新失败");
        }
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> deleteQuestion(@PathVariable Long id) {
        boolean success = careerAnchorQuestionService.deleteQuestion(id);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("500", "删除失败");
        }
    }
} 