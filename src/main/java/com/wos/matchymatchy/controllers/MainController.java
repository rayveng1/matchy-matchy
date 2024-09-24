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
import org.springframework.web.bind.annotation.*;

import java.util.*;

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



        ArrayList<Place> places = new ArrayList<>();
        List<Place> restaurantPlaces = apiService.getApiResponse(location, "restaurant").getPlaces();

        if (restaurantPlaces != null) {
            places.addAll(restaurantPlaces);
        }

        List<Place> bankPlaces = apiService.getApiResponse(location, "bank").getPlaces();
        if (bankPlaces != null) {
            places.addAll(bankPlaces);
        }

        List<Place> carRepairPlaces = apiService.getApiResponse(location, "car_repair").getPlaces();
        if (carRepairPlaces != null) {
            places.addAll(carRepairPlaces);
        }

        List<Place> insuranceAgencyPlaces = apiService.getApiResponse(location, "insurance_agency").getPlaces();
        if (insuranceAgencyPlaces != null) {
            places.addAll(insuranceAgencyPlaces);
        }

        List<Place> movieTheaterPlaces = apiService.getApiResponse(location, "movie_theater").getPlaces();
        if (movieTheaterPlaces != null) {
            places.addAll(movieTheaterPlaces);
        }

        List<Place> travelAgencyPlaces = apiService.getApiResponse(location, "travel_agency").getPlaces();
        if (travelAgencyPlaces != null) {
            places.addAll(travelAgencyPlaces);
        }

        List<Place> hotelPlaces = apiService.getApiResponse(location, "hotel").getPlaces();
        if (hotelPlaces != null) {
            places.addAll(hotelPlaces);
        }

        List<Place> fitnessCenterPlaces = apiService.getApiResponse(location, "fitness_center").getPlaces();
        if (fitnessCenterPlaces != null) {
            places.addAll(fitnessCenterPlaces);
        }

        List<Place> amusementParkPlaces = apiService.getApiResponse(location, "amusement_park").getPlaces();
        if (amusementParkPlaces != null) {
            places.addAll(amusementParkPlaces);
        }

        List<Place> departmentStorePlaces = apiService.getApiResponse(location, "department_store").getPlaces();
        if (departmentStorePlaces != null) {
            places.addAll(departmentStorePlaces);
        }

        JSONObject jsObject = new JSONObject();
        for (Place place : places) {
            jsObject.put("longitude", place.getLocation().getLongitude());
            jsObject.put("latitude", place.getLocation().getLatitude());
            jsObject.put("category", place.getMainCategory());
        }

        model.addAttribute("places", places);
        model.addAttribute("categories", getCategorizedPlaces(places));


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
            System.out.println("Category: " + category);
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
            System.out.println("test - "+ place.getEditorialSummary().getText());

            System.out.println("Place: " + place.getDisplayName().getText() + ", Types: " + place.getTypes());

            String category = getMainCategory(place.getTypes());
            if (!hm.containsKey(category)){
                hm.put(category, new ArrayList<>(){{add(place);}});
            } else {
                hm.get(category).add(place);
            }
        }

        return hm;
    }

    @GetMapping("/places")
    @ResponseBody
    public List<Map<String, Object>> getPlaces(HttpSession session) throws Exception {
        if (session.getAttribute("latitude") == null) {
            return Collections.emptyList(); // return an empty list if no location is found
        }

        double lat = (double) session.getAttribute("latitude");
        double lng = (double) session.getAttribute("longitude");

        Location location = new Location(lat, lng);
        ArrayList<Place> places = new ArrayList<>();

        List<Place> restaurantPlaces = apiService.getApiResponse(location, "restaurant").getPlaces();
        if (restaurantPlaces != null)
            places.addAll(restaurantPlaces);

        List<Place> bankPlaces = apiService.getApiResponse(location, "bank").getPlaces();
        if (bankPlaces != null) {
            places.addAll(bankPlaces);
        }

        List<Place> carRepairPlaces = apiService.getApiResponse(location, "car_repair").getPlaces();
        if (carRepairPlaces != null) {
            places.addAll(carRepairPlaces);
        }

        List<Place> insuranceAgencyPlaces = apiService.getApiResponse(location, "insurance_agency").getPlaces();
        if (insuranceAgencyPlaces != null) {
            places.addAll(insuranceAgencyPlaces);
        }

        List<Place> movieTheaterPlaces = apiService.getApiResponse(location, "movie_theater").getPlaces();
        if (movieTheaterPlaces != null) {
            places.addAll(movieTheaterPlaces);
        }

        List<Place> travelAgencyPlaces = apiService.getApiResponse(location, "travel_agency").getPlaces();
        if (travelAgencyPlaces != null) {
            places.addAll(travelAgencyPlaces);
        }

        List<Place> hotelPlaces = apiService.getApiResponse(location, "hotel").getPlaces();
        if (hotelPlaces != null) {
            places.addAll(hotelPlaces);
        }

        List<Place> fitnessCenterPlaces = apiService.getApiResponse(location, "fitness_center").getPlaces();
        if (fitnessCenterPlaces != null) {
            places.addAll(fitnessCenterPlaces);
        }

        List<Place> amusementParkPlaces = apiService.getApiResponse(location, "amusement_park").getPlaces();
        if (amusementParkPlaces != null) {
            places.addAll(amusementParkPlaces);
        }

        List<Place> departmentStorePlaces = apiService.getApiResponse(location, "department_store").getPlaces();
        if (departmentStorePlaces != null) {
            places.addAll(departmentStorePlaces);
        }


        List<Map<String, Object>> placesData = new ArrayList<>();
        for (Place place : places) {
            Map<String, Object> placeData = new HashMap<>();
            placeData.put("longitude", place.getLocation().getLongitude());
            placeData.put("latitude", place.getLocation().getLatitude());
            placeData.put("category", place.getMainCategory());
            placesData.add(placeData);

            System.out.println("Place: " + place.getDisplayName().getText() + ", Category: " + place.getMainCategory());

        }
        return placesData;
    }





}
