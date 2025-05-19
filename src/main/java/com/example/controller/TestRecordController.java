package com.example.controller;

import com.example.common.Result;
import com.example.entity.TestRecord;
import com.example.service.TestRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test-records")
@CrossOrigin
public class TestRecordController {

    @Autowired
    private TestRecordService testRecordService;

    @GetMapping
    public Result<List<TestRecord>> getAllRecords() {
        List<TestRecord> records = testRecordService.getAllRecords();
        return Result.success(records);
    }

    @GetMapping("/{id}")
    public Result<TestRecord> getRecordById(@PathVariable Long id) {
        TestRecord record = testRecordService.getRecordById(id);
        if (record != null) {
            return Result.success(record);
        } else {
            return Result.error("404", "测评记录不存在");
        }
    }

    @GetMapping("/user/{userId}")
    public Result<List<TestRecord>> getRecordsByUserId(@PathVariable Long userId) {
        List<TestRecord> records = testRecordService.getRecordsByUserId(userId);
        return Result.success(records);
    }

    @GetMapping("/user/{userId}/latest/{testType}")
    public Result<TestRecord> getLatestRecordByUserIdAndType(
            @PathVariable Long userId, 
            @PathVariable String testType) {
        TestRecord record = testRecordService.getLatestRecordByUserIdAndType(userId, testType);
        if (record != null) {
            return Result.success(record);
        } else {
            return Result.error("404", "未找到该类型的测评记录");
        }
    }

    @PostMapping
    public Result<Boolean> addRecord(@RequestBody TestRecord testRecord) {
        boolean success = testRecordService.addRecord(testRecord);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("500", "添加失败");
        }
    }

    @PutMapping("/{id}")
    public Result<Boolean> updateRecord(@PathVariable Long id, @RequestBody TestRecord testRecord) {
        testRecord.setId(id);
        boolean success = testRecordService.updateRecord(testRecord);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("500", "更新失败");
        }
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> deleteRecord(@PathVariable Long id) {
        boolean success = testRecordService.deleteRecord(id);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("500", "删除失败");
        }
    }
} 