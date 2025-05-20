package com.example.service.impl;

import com.example.entity.User;
import com.example.exception.CustomerException;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public User findByUsername(String username) {
        System.out.println("正在查询用户: " + username);
        try {
            User user = userMapper.findByUsername(username);
            System.out.println("查询结果: " + (user != null ? "找到用户" : "未找到用户"));
            if (user != null) {
                System.out.println("用户ID: " + user.getId());
                System.out.println("用户名: " + user.getUsername());
                System.out.println("用户角色: " + user.getRole());
            }
            return user;
        } catch (Exception e) {
            System.err.println("查询用户出错: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public boolean register(User user) {
        // 检查用户名是否已存在
        User existingUser = userMapper.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new CustomerException("用户名已存在");
        }
        
        // 设置默认角色
        if (user.getRole() == null) {
            user.setRole("user");
        }
        
        // 设置创建时间
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        
        // 设置默认状态
        user.setStatus(1);
        
        // 设置默认头像
        if (user.getAvatar() == null) {
            user.setAvatar("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
        }
        
        return userMapper.insert(user) > 0;
    }

    @Override
    public boolean update(User user) {
        System.out.println("====== 开始更新用户信息 ======");
        System.out.println("用户ID: " + user.getId());
        System.out.println("用户名: " + user.getUsername());
        System.out.println("密码: " + user.getPassword());
        System.out.println("邮箱: " + user.getEmail());
        System.out.println("手机: " + user.getPhone());
        System.out.println("真实姓名: " + user.getRealName());
        System.out.println("更新时间: " + LocalDateTime.now());
        
        user.setUpdateTime(LocalDateTime.now());
        int result = userMapper.update(user);
        System.out.println("更新结果: " + result + " (影响行数)");
        System.out.println("====== 用户信息更新结束 ======");
        
        return result > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return userMapper.deleteById(id) > 0;
    }

    @Override
    public String login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new CustomerException("用户不存在");
        }
        
        if (!password.equals(user.getPassword())) {
            throw new CustomerException("密码错误");
        }
        
        // 生成JWT令牌并返回
        return jwtUtil.generateToken(username);
    }
    
    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public boolean updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new CustomerException("用户不存在");
        }
        
        // 验证旧密码
        if (!oldPassword.equals(user.getPassword())) {
            throw new CustomerException("原密码不正确");
        }
        
        // 更新密码
        user.setPassword(newPassword);
        user.setUpdateTime(LocalDateTime.now());
        
        return userMapper.update(user) > 0;
    }
} 