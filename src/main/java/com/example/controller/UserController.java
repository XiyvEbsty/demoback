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
        // 从请求头中获取 Authorization 值
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = jwtUtil.extractUsername(token);
            User user = userService.findByUsername(username);
            
            if (user != null) {
                // 出于安全考虑，不返回密码
                user.setPassword(null);
                return Result.success(user);
            }
            return Result.error("404", "用户不存在");
        }
        return Result.error("401", "未登录或登录已过期");
    }
    
    @PutMapping("/current")
    public Result<Boolean> updateCurrentUser(@RequestBody User updatedUser, HttpServletRequest request) {
        try {
            System.out.println("收到用户信息更新请求: " + updatedUser.getUsername());
            System.out.println("更新数据详情: " + updatedUser);
            
            // 从请求头中获取 Authorization 值
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                String username = jwtUtil.extractUsername(token);
                System.out.println("从令牌中提取的用户名: " + username);
                
                User user = userService.findByUsername(username);
                System.out.println("当前数据库中的用户: " + (user != null ? user.getId() : "null"));
                
                if (user != null) {
                    // 首先处理密码修改
                    String currentPassword = updatedUser.getCurrentPassword();
                    String newPassword = updatedUser.getNewPassword();
                    boolean passwordUpdated = false;
                    
                    if (currentPassword != null && newPassword != null) {
                        System.out.println("开始处理密码修改");
                        System.out.println("当前密码: " + currentPassword);
                        System.out.println("新密码: " + newPassword);
                        
                        // 单独处理密码更新
                        try {
                            boolean success = userService.updatePassword(user.getId(), currentPassword, newPassword);
                            if (success) {
                                passwordUpdated = true;
                                System.out.println("密码更新成功");
                            } else {
                                System.out.println("密码更新失败");
                                return Result.error("500", "密码修改失败");
                            }
                        } catch (Exception e) {
                            System.out.println("密码更新异常: " + e.getMessage());
                            return Result.error("400", e.getMessage());
                        }
                    }
                    
                    // 如果只是修改密码，不需要更新其他信息
                    if (passwordUpdated && 
                        (updatedUser.getUsername() == null || updatedUser.getUsername().equals(user.getUsername())) &&
                        (updatedUser.getEmail() == null || (updatedUser.getEmail().equals(user.getEmail()))) &&
                        (updatedUser.getPhone() == null || (updatedUser.getPhone().equals(user.getPhone()))) &&
                        (updatedUser.getRealName() == null || (updatedUser.getRealName() != null && updatedUser.getRealName().equals(user.getRealName()))) &&
                        (updatedUser.getAvatar() == null || (updatedUser.getAvatar() != null && updatedUser.getAvatar().equals(user.getAvatar())))) {
                        System.out.println("只更新了密码，不需要更新其他信息");
                        return Result.success(true);
                    }
                    
                    // 创建新的用户对象用于更新其他信息
                    User userToUpdate = new User();
                    userToUpdate.setId(user.getId());
                    userToUpdate.setUsername(updatedUser.getUsername() != null ? updatedUser.getUsername() : user.getUsername());
                    userToUpdate.setEmail(updatedUser.getEmail() != null ? updatedUser.getEmail() : user.getEmail());
                    userToUpdate.setPhone(updatedUser.getPhone() != null ? updatedUser.getPhone() : user.getPhone());
                    userToUpdate.setRealName(updatedUser.getRealName() != null ? updatedUser.getRealName() : user.getRealName());
                    userToUpdate.setAvatar(updatedUser.getAvatar() != null ? updatedUser.getAvatar() : user.getAvatar());
                    userToUpdate.setRole(user.getRole());
                    userToUpdate.setStatus(user.getStatus());
                    
                    // 已经单独处理了密码更新，这里不再设置密码
                    userToUpdate.setPassword(null);
                    
                    // 检查用户名是否已被使用
                    if (updatedUser.getUsername() != null && !updatedUser.getUsername().equals(user.getUsername())) {
                        User existingUser = userService.findByUsername(updatedUser.getUsername());
                        if (existingUser != null) {
                            return Result.error("400", "用户名已被使用");
                        }
                    }
                    
                    // 保存其他信息的更新
                    boolean success = userService.update(userToUpdate);
                    System.out.println("其他信息更新结果: " + (success ? "成功" : "失败"));
                    
                    return Result.success(true);
                }
            }
            return Result.error("401", "未登录或登录已过期");
        } catch (Exception e) {
            e.printStackTrace();
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
    public Result<Boolean> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        try {
            // 获取当前用户信息
            User existingUser = userService.findById(id);
            if (existingUser == null) {
                return Result.error("404", "用户不存在");
            }
            
            // 只更新提供的字段
            updatedUser.setId(id);
            
            // 保留原密码，防止被设置为null
            if (updatedUser.getPassword() == null) {
                updatedUser.setPassword(existingUser.getPassword());
            }
            
            boolean success = userService.update(updatedUser);
            if (success) {
                return Result.success(true);
            } else {
                return Result.error("500", "更新失败");
            }
        } catch (Exception e) {
            return Result.error("500", e.getMessage());
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
