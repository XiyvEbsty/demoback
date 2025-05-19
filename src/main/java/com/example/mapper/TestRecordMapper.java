package com.example.mapper;

import com.example.entity.TestRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TestRecordMapper {

    List<TestRecord> findAll();
    
    TestRecord findById(@Param("id") Long id);
    
    List<TestRecord> findByUserId(@Param("userId") Long userId);
    
    TestRecord findLatestByUserIdAndType(@Param("userId") Long userId, @Param("testType") String testType);
    
    int insert(TestRecord testRecord);
    
    int update(TestRecord testRecord);
    
    int deleteById(@Param("id") Long id);
} 