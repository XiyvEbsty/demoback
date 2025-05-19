package com.example.service.impl;

import com.example.entity.TestRecord;
import com.example.mapper.TestRecordMapper;
import com.example.service.TestRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestRecordServiceImpl implements TestRecordService {

    @Autowired
    private TestRecordMapper testRecordMapper;

    @Override
    public List<TestRecord> getAllRecords() {
        return testRecordMapper.findAll();
    }

    @Override
    public TestRecord getRecordById(Long id) {
        return testRecordMapper.findById(id);
    }

    @Override
    public List<TestRecord> getRecordsByUserId(Long userId) {
        return testRecordMapper.findByUserId(userId);
    }

    @Override
    public TestRecord getLatestRecordByUserIdAndType(Long userId, String testType) {
        return testRecordMapper.findLatestByUserIdAndType(userId, testType);
    }

    @Override
    public boolean addRecord(TestRecord testRecord) {
        return testRecordMapper.insert(testRecord) > 0;
    }

    @Override
    public boolean updateRecord(TestRecord testRecord) {
        return testRecordMapper.update(testRecord) > 0;
    }

    @Override
    public boolean deleteRecord(Long id) {
        return testRecordMapper.deleteById(id) > 0;
    }
} 