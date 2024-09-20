package com.wos.matchymatchy.controllers;

import com.wos.matchymatchy.models.ApiResponse;
import com.wos.matchymatchy.models.Location;
import com.wos.matchymatchy.models.Place;
import com.wos.matchymatchy.services.ApiService;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
public class MainController {

    private final ApiService apiService;

    public MainController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/")
    public String index(HttpSession session, Model model) throws Exception {

        if (session.getAttribute("latitude") == null){
            return "index2.jsp";

        }
        double lat = (double) session.getAttribute("latitude");
        double lng = (double) session.getAttribute("longitude");

        Location location = new Location(lat, lng);

        //if statement category == department_store && Count != 2
        // return 1 department_store and count++
        //limit api
        //shorten list before displaying result.
        //category...size

        ArrayList<Place> places = new ArrayList<>();
        ApiResponse temp = new ApiResponse();

//        ArrayList<String> categories = new ArrayList<>(Arrays.asList("restaurant","bank","car_repair","insurance_agency","movie_theater","travel_agency","hotel","fitness_center","amusement_park","department_store"));
        places.addAll(apiService.getApiResponse(location, "restaurant").getPlaces());
        places.addAll(apiService.getApiResponse(location, "bank").getPlaces());
        places.addAll(apiService.getApiResponse(location, "car_repair").getPlaces());
        places.addAll(apiService.getApiResponse(location, "insurance_agency").getPlaces());
        places.addAll(apiService.getApiResponse(location, "movie_theater").getPlaces());
        places.addAll(apiService.getApiResponse(location, "travel_agency").getPlaces());
//        places.addAll(apiService.getApiResponse(location, "hotel").getPlaces());
        places.addAll(apiService.getApiResponse(location, "fitness_center").getPlaces());
//        places.addAll(apiService.getApiResponse(location, "amusement_park").getPlaces());
        places.addAll(apiService.getApiResponse(location, "department_store").getPlaces());


        model.addAttribute("places", places);
        model.addAttribute("categories", getCategorizedPlaces(places));

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
            System.out.println("test - "+categories);
        for (String category : categories) {
            if ((category.contains("car_repair") && !category.contains("care"))) {
                return "Automotive";
            }
            switch (category) {
                case "department_store":
                    return "Department Store";
                case "restaurant":
                    return "Food";
                case "bank":
                    return "Finance";
                case "insurance_agency":
                    return "Insurance";
                case "movie_theater":
                    return "Entertainment";
                case "travel_agency":
                    return "Travel";
                case "hotel":
                    return "Hotel/Lodging";
                case "fitness_center":
                    return "Fitness Centers";
                case "amusement_park":
                    return "Theme Parks";// Optional: Handle any unknown category
            }
        }
        return "unknown_category";
    }


    public HashMap<String, List<Place>> getCategorizedPlaces(List<Place> places){
        HashMap<String, List<Place>> hm = new HashMap<>();

        for (Place place : places){
            System.out.println("test - "+ place.getDisplayName().getText());
            String category = getMainCategory(place.getTypes());
            if (!hm.containsKey(category)){
                hm.put(category, new ArrayList<>(){{add(place);}});
            } else {
                hm.get(category).add(place);
            }
        }
        System.out.println(hm);
        return hm;
    }
}
