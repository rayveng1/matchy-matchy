package com.wos.matchymatchy.controllers;

import com.wos.matchymatchy.models.ApiResponse;
import com.wos.matchymatchy.models.Location;
import com.wos.matchymatchy.services.ApiService;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MainController {

    private final ApiService apiService;

    public MainController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/")
    public String index(HttpSession session, Model model) throws Exception {

        double lat = (double) session.getAttribute("latitude");
        double lng = (double) session.getAttribute("longitude");

        Location location = new Location(lat, lng);

        ApiResponse response = apiService.getApiResponse(location);
        model.addAttribute("response", response);
        return "index2.jsp";
    }

    @PostMapping("/savelocation")
    public String location(@RequestBody String request, HttpSession session) {
        JSONObject jsonObject = new JSONObject(request);
        System.out.println(jsonObject.get("Latitude") == JSONObject.NULL);
        if (jsonObject.get("Latitude") == JSONObject.NULL) {
            System.out.println(jsonObject.get("Latitude"));
            session.removeAttribute("latitude");
            session.removeAttribute("longitude");
        } else {
            session.setAttribute("latitude", jsonObject.get("Latitude"));
            session.setAttribute("longitude", jsonObject.get("Longitude"));
        }
        return "index2.jsp";
    }


}
