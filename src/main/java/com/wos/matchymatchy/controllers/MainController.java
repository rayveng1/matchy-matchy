package com.wos.matchymatchy.controllers;

import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class MainController {

    public MainController() { }

    @GetMapping("/")



    public String index(Model model, HttpSession session) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(4);
        list.add(6);
        model.addAttribute("categories", list);
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

//    public HashMap<String, List<Place>> getCategorizedPlaces(List<Place> places){
//        HashMap<String, List<Place>> hm = new HashMap<>();
//
//        for (Place place : places){
//            String category = getMainCategory(place.Types);
//            if (!hm.containsKey(category)){
//                hm.put(category, new ArrayList<>().add(place));
//            } else {
//                hm.get(category).add(place);
//            }
//        }
//
//        return hm;
//    }

    public static String getMainCategory(List<String> categories){
        for (String category : categories) {
            switch (category) {
                case "department_store":
                    return "department_store";
                case "food":
                    return "food";
                case "car":
                    return "car";
                case "finance":
                    return "finance";
                case "insurance":
                    return "insurance";
                case "entertainment":
                    return "entertainment";
                case "travel":
                    return "travel";
                case "hotel_lodging":
                    return "hotel_lodging";
                case "fitness_centers":
                    return "fitness_centers";
                case "theme_parks":
                    return "theme_parks";
                default:
                    return "unknown_category"; // Optional: Handle any unknown category
            }
        }
        return "unknown_category";
    }
}
