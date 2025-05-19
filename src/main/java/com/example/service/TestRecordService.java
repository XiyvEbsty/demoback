package com.example.service;

import com.example.entity.TestRecord;

import java.util.List;

public interface TestRecordService {

    List<TestRecord> getAllRecords();
    
    TestRecord getRecordById(Long id);
    
    List<TestRecord> getRecordsByUserId(Long userId);
    
    TestRecord getLatestRecordByUserIdAndType(Long userId, String testType);
    
    boolean addRecord(TestRecord testRecord);
    
    boolean updateRecord(TestRecord testRecord);
    
    boolean deleteRecord(Long id);
} 