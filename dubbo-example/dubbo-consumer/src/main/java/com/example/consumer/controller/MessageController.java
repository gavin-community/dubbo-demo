package com.example.consumer.controller;

import com.example.consumer.service.IConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("demo")
public class MessageController {
    @Autowired
    private IConsumerService consumerService;

    @RequestMapping("/message")
    @ResponseBody
    private String doMessage() {
        return consumerService.getMessage();
    }

}
