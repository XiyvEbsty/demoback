package com.example.service;

import com.example.entity.User;
import com.example.exception.CustomerException;
import com.example.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Resource
    UserMapper userMapper;

    public  String admin(String name) {
        if ("admin".equals(name)) {
            return "admin";
        }
        else{
            throw new CustomerException("账号错误");
        }
    }
    public List<User> selectAll(){
        return userMapper.selectAll();
    }
}
