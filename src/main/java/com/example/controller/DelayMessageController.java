package com.example.controller;

import com.example.producer.DelayMessageProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/delay")
public class DelayMessageController {

    @Resource
    private DelayMessageProducer delayMessageProducer;

    @PostMapping("/send")
    public String sendDelayMessage(
            @RequestParam("content") String content,
            @RequestParam("delayTime") long delayTime) {
        delayMessageProducer.sendDelayMessage(content, delayTime);
        return "发送成功";
    }
}
