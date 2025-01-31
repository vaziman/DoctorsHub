package com.doctorshub.doctorshub.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class MainController {

    @GetMapping("/")
    public String index() {
        return "home-page";
    }
}
