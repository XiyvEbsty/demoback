package com.example.service;

import com.example.entity.User;
import com.example.exception.CustomerException;
import com.example.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    User findByUsername(String username);
    
    User findById(Long id);
    
    boolean register(User user);
    
    boolean update(User user);
    
    boolean updatePassword(Long userId, String oldPassword, String newPassword);
    
    boolean deleteById(Long id);
    
    String login(String username, String password);
    
    List<User> selectAll();
}
