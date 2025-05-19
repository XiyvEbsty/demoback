package com.example.controller;

import com.example.common.Result;
import com.example.dto.LoginRequest;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public Result<List<User>> getAllUsers() {
        List<User> users = userService.selectAll();
        return Result.success(users);
    }
    
    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.error("404", "用户不存在");
        }
    }
    
    @GetMapping("/current")
    public Result<User> getCurrentUser(HttpServletRequest request) {
        try {
            System.out.println("获取当前用户信息请求");
            // 从请求头中获取 Authorization 值
            String authHeader = request.getHeader("Authorization");
            System.out.println("Authorization头：" + authHeader);
            
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                System.out.println("提取的令牌：" + token);
                
                String username = jwtUtil.extractUsername(token);
                System.out.println("从令牌中提取的用户名：" + username);
                
                User user = userService.findByUsername(username);
                System.out.println("查询到的用户：" + (user != null ? user.getUsername() : "null"));
                
                if (user != null) {
                    // 不返回敏感信息
                    user.setPassword(null);
                    return Result.success(user);
                }
            } else {
                System.out.println("请求中没有Bearer令牌");
            }
            return Result.error("401", "未登录或登录已过期");
        } catch (Exception e) {
            System.err.println("获取当前用户信息异常：" + e.getMessage());
            e.printStackTrace();
            return Result.error("500", e.getMessage());
        }
    }
    
    @PutMapping("/current")
    public Result<Boolean> updateCurrentUser(@RequestBody User updatedUser, HttpServletRequest request) {
        try {
            // 从请求头中获取 Authorization 值
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                String username = jwtUtil.extractUsername(token);
                User user = userService.findByUsername(username);
                
                if (user != null) {
                    // 只允许更新部分字段
                    if (updatedUser.getEmail() != null) {
                        user.setEmail(updatedUser.getEmail());
                    }
                    if (updatedUser.getPhone() != null) {
                        user.setPhone(updatedUser.getPhone());
                    }
                    if (updatedUser.getRealName() != null) {
                        user.setRealName(updatedUser.getRealName());
                    }
                    if (updatedUser.getAvatar() != null) {
                        user.setAvatar(updatedUser.getAvatar());
                    }
                    
                    // 保存更新
                    boolean success = userService.update(user);
                    if (success) {
                        return Result.success(true);
                    } else {
                        return Result.error("500", "更新失败");
                    }
                }
            }
            return Result.error("401", "未登录或登录已过期");
        } catch (Exception e) {
            return Result.error("500", e.getMessage());
        }
    }
    
    @PostMapping("/register")
    public Result<Boolean> register(@RequestBody User user) {
        boolean success = userService.register(user);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("500", "注册失败");
        }
    }
    
    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return Result.success(token);
        } catch (Exception e) {
            return Result.error("401", e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public Result<Boolean> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        boolean success = userService.update(user);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("500", "更新失败");
        }
    }
    
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteUser(@PathVariable Long id) {
        boolean success = userService.deleteById(id);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("500", "删除失败");
        }
    }
}
