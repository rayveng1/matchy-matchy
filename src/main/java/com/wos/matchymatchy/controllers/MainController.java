package com.wos.matchymatchy.controllers;

import com.wos.matchymatchy.models.ApiResponse;
import com.wos.matchymatchy.models.Location;
import com.wos.matchymatchy.models.MainPlace;
import com.wos.matchymatchy.models.Place;
import com.wos.matchymatchy.services.ApiService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class MainController {

    private final ApiService apiService;

    public MainController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/")
    public String index(@ModelAttribute("mainPlace") MainPlace mainPlace, HttpSession session, Model model) throws Exception {

        if (session.getAttribute("latitude") == null){
            return "index2.jsp";

        }
        double lat = (double) session.getAttribute("latitude");
        double lng = (double) session.getAttribute("longitude");

        Location location = new Location(lat, lng);

        ApiResponse response = apiService.getApiResponse(location);

        model.addAttribute("places", response.getPlaces());
        model.addAttribute("categories", getCategorizedPlaces(response.getPlaces()));
        model.addAttribute("mainPlace", mainPlace);
        return "index2.jsp";
    }

    @PostMapping("/savelocation")
    public String location(@RequestBody String request, HttpSession session) {
        JSONObject jsonObject = new JSONObject(request);
//        System.out.println(jsonObject.get("Latitude") == JSONObject.NULL);
        if (jsonObject.get("Latitude") == JSONObject.NULL) {
//            System.out.println(jsonObject.get("Latitude"));
            session.removeAttribute("latitude");
            session.removeAttribute("longitude");
        } else {
            session.setAttribute("latitude", jsonObject.get("Latitude"));
            session.setAttribute("longitude", jsonObject.get("Longitude"));
        }
        return "index2.jsp";
    }

    public static String getMainCategory(List<String> categories){
        for (String category : categories) {
            if ((category.contains("car") && !category.contains("care"))) {
                return "Car";
            }
            switch (category) {
                case "department_store":
                    return "Department Store";
                case "food":
                    return "Food";
                case "finance":
                    return "Finance";
                case "insurance":
                    return "Insurance";
                case "entertainment":
                    return "Entertainment";
                case "travel":
                    return "Travel";
                case "hotel_lodging":
                    return "Hotel/Lodging";
                case "fitness_centers":
                    return "Fitness Centers";
                case "theme_parks":
                    return "Theme Parks";// Optional: Handle any unknown category
            }
        }
        return "unknown_category";
    }


    public HashMap<String, List<Place>> getCategorizedPlaces(List<Place> places){
        HashMap<String, List<Place>> hm = new HashMap<>();

        for (Place place : places){
            System.out.println("test - "+ place.getEditorialSummary().getText());

            String category = getMainCategory(place.getTypes());
            if (!hm.containsKey(category)){
                hm.put(category, new ArrayList<>(){{add(place);}});
            } else {
                hm.get(category).add(place);
            }
        }
        return hm;
    }
}
