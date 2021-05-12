package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: 11655
 * @Date: 2021/5/3 5:21
 * @Description:
 */
@RestController
public class MainController {
    @GetMapping("/api/q")
    public String hh(HttpServletRequest request) {
        request.getSession();
        return "123";
    }

    @GetMapping("/api/a")
    public String aa(HttpServletRequest request) {
        HttpSession servletRequestSession=request.getSession();
        return "aaaa";
    }
}
