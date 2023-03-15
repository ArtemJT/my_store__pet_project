package com.example.my_store__pet_project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/greeting")
@Slf4j
@RequiredArgsConstructor
public class GreetingController {

    @GetMapping
    public String greeting() {
        log.info("Greeting page called");
        return "greeting";
    }
}
