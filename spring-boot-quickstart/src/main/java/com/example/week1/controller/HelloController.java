package com.example.week1.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String getHello() {
        return "Hello World";
    }

    @GetMapping("/list")
    public List<String> getList() {
        return List.of("aaa", "bbb", "ccc");
    }

}