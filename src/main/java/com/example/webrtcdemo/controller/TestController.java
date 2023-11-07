package com.example.webrtcdemo.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("websocket")
public class TestController {

    @RequestMapping("test")
    public String test() {
        return "Hello World";
    }
}