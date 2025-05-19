package com.example.controller;

import com.example.common.Result;
import com.example.entity.HollandQuestion;
import com.example.service.HollandQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/holland-questions")
@CrossOrigin
public class HollandQuestionController {

    @Autowired
    private HollandQuestionService hollandQuestionService;

    @GetMapping
    public Result<List<HollandQuestion>> getAllQuestions() {
        List<HollandQuestion> questions = hollandQuestionService.getAllQuestions();
        return Result.success(questions);
    }

    @GetMapping("/{id}")
    public Result<HollandQuestion> getQuestionById(@PathVariable Long id) {
        HollandQuestion question = hollandQuestionService.getQuestionById(id);
        if (question != null) {
            return Result.success(question);
        } else {
            return Result.error("404", "问题不存在");
        }
    }

    @GetMapping("/type/{type}")
    public Result<List<HollandQuestion>> getQuestionsByType(@PathVariable String type) {
        List<HollandQuestion> questions = hollandQuestionService.getQuestionsByType(type);
        return Result.success(questions);
    }

    @PostMapping
    public Result<Boolean> addQuestion(@RequestBody HollandQuestion question) {
        boolean success = hollandQuestionService.addQuestion(question);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("500", "添加失败");
        }
    }

    @PutMapping("/{id}")
    public Result<Boolean> updateQuestion(@PathVariable Long id, @RequestBody HollandQuestion question) {
        question.setId(id);
        boolean success = hollandQuestionService.updateQuestion(question);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("500", "更新失败");
        }
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> deleteQuestion(@PathVariable Long id) {
        boolean success = hollandQuestionService.deleteQuestion(id);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("500", "删除失败");
        }
    }
} 