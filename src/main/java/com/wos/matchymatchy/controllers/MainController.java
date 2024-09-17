package com.wos.matchymatchy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    public MainController() { }

    @GetMapping("/")
    public String index() {
        return "index2.jsp";
    }
}
