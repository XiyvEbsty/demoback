package com.zwtj.demoback.controller;

import com.zwtj.demoback.common.Result;
import com.zwtj.demoback.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController


public class helloController {

    @Resource
    AdminService adminService;

    @GetMapping("/hello")
    public Result hello(){
        return Result.success("hello");
    }
    @GetMapping("/admin")
    public Result admin(String name) {
        String admin = adminService.admin(name);
        return Result.success(admin);
    }
}
