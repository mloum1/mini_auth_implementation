package com.exo.sec_web.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ClientController {

    @GetMapping("/public/test")
    public String greet() {
        return " Public Hello and welcome";
    }

    @GetMapping("/private/test")
    public String greetPrivate() {
        return "My private get my warmest greetings";
    }
}
