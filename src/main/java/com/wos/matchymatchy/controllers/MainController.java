package com.wos.matchymatchy.controllers;

import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MainController {

    public MainController() { }

    @GetMapping("/")
    public String index() {
        return "index2.jsp";
    }

    @PostMapping("/savelocation")
    public String location(@RequestBody String request, HttpSession session) {
        JSONObject jsonObject = new JSONObject(request);
        session.setAttribute("latitude", jsonObject.get("Latitude"));
        session.setAttribute("latitude", jsonObject.get("Longitude"));
        return "index2.jsp";
    }


}
