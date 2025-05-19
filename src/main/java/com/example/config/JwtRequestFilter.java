package com.example.config;

import com.example.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    
    // 不需要验证Token的接口列表
    private final List<String> excludedPaths = Arrays.asList(
        "/api/users/login",
        "/api/users/register",
        "/error",
        "/api/public/",  // 公共API前缀路径
        "/api/knowledge-graph/status",  // 知识图谱状态API
        "/api/knowledge-graph/initialize"  // 知识图谱初始化API
    );
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // 获取请求路径和方法
        String path = request.getRequestURI();
        String method = request.getMethod();
        
        // 打印请求路径和Authorization头，用于调试
        System.out.println("Request Path: " + path);
        System.out.println("Request Method: " + method);
        System.out.println("Authorization Header: " + request.getHeader("Authorization"));
        
        // 允许OPTIONS请求通过（预检请求）
        if ("OPTIONS".equalsIgnoreCase(method)) {
            System.out.println("允许OPTIONS预检请求通过");
            filterChain.doFilter(request, response);
            return;
        }
        
        // 如果是排除的路径，则不需要验证Token
        if (isExcludedPath(path)) {
            System.out.println("排除路径: " + path + "，跳过Token验证");
            filterChain.doFilter(request, response);
            return;
        }
        
        final String authorizationHeader = request.getHeader("Authorization");
        
        String username = null;
        String jwt = null;
        
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            System.out.println("提取的JWT令牌: " + jwt);
            try {
                username = jwtUtil.extractUsername(jwt);
                System.out.println("从JWT令牌提取的用户名: " + username);
            } catch (Exception e) {
                // 令牌无效或已过期
                System.err.println("JWT令牌验证失败: " + e.getMessage());
                e.printStackTrace();
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Unauthorized: Invalid or expired token");
                return;
            }
        }
        
        if (username != null) {
            System.out.println("JWT令牌验证成功，用户: " + username);
            // 这里可以进一步验证用户是否存在于数据库中
            // 如果有需要，还可以将用户信息存储到SecurityContextHolder中
            
            filterChain.doFilter(request, response);
        } else {
            System.out.println("请求缺少有效的JWT令牌");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Unauthorized: Authentication token is required");
        }
    }
    
    private boolean isExcludedPath(String path) {
        return excludedPaths.stream().anyMatch(path::startsWith);
    }
} 