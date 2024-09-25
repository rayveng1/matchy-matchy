package com.wos.matchymatchy.controllers;

import com.wos.matchymatchy.models.*;
import com.wos.matchymatchy.services.ApiService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.text.DecimalFormat;


import java.util.Random;

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
        if (session.getAttribute("places") == null){
            Random random = new Random();
            ArrayList<Place> places = new ArrayList<>();

            addRandomPlace(places, location, "restaurant", "Food", random);
            addRandomPlace(places, location, "bank", "Finance", random);
            addRandomPlace(places, location, "car_repair", "Automotive", random);
            addRandomPlace(places, location, "insurance_agency", "Insurance", random);
            addRandomPlace(places, location, "movie_theater", "Entertainment", random);
            addRandomPlace(places, location, "travel_agency", "Travel", random);
            addRandomPlace(places, location, "hotel", "Hotel/Lodging", random);
            addRandomPlace(places, location, "fitness_center", "Fitness Centers", random);
            addRandomPlace(places, location, "amusement_park", "Theme Parks", random);
            addRandomPlace(places, location, "department_store", "Department Store", random);


            DecimalFormat df = new DecimalFormat("#.#");
            for (Place place : places) {
                place.setDistance(df.format(haversineDistance((Double) session.getAttribute("latitude"), (Double) session.getAttribute("longitude"), place.getLocation().getLatitude(), place.getLocation().getLongitude())));
                for (Photo photo : place.getPhotos()){
                    place.setImageGetRequest(apiService.getPhotosApiResponse(photo.getName()));
                }
            }


            session.setAttribute("places", places);
        }
        model.addAttribute("places", session.getAttribute("places"));
        model.addAttribute("categories", getCategorizedPlaces((List<Place>) session.getAttribute("places")));
        model.addAttribute("mainPlace", mainPlace);

        return "index2.jsp";
    }

    @PostMapping("/savelocation")
    public String location(@RequestBody String request, HttpSession session) {
        JSONObject jsonObject = new JSONObject(request);
        if (jsonObject.get("Latitude") == JSONObject.NULL) {
            session.removeAttribute("latitude");
            session.removeAttribute("longitude");
        } else {
            session.setAttribute("latitude", jsonObject.get("Latitude"));
            session.setAttribute("longitude", jsonObject.get("Longitude"));
        }
        return "index2.jsp";
    }

    public static String getMainCategory(List<String> categories) {
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

    public HashMap<String, List<Place>> getCategorizedPlaces(List<Place> places) {
        HashMap<String, List<Place>> hm = new HashMap<>();



        for (Place place : places){
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
        List<Place> places = (List<Place>) session.getAttribute("places");
        List<Map<String, Object>> placesData = new ArrayList<>();
        for (Place place : places) {
            Map<String, Object> placeData = new HashMap<>();
            placeData.put("longitude", place.getLocation().getLongitude());
            placeData.put("latitude", place.getLocation().getLatitude());
            String category = getMainCategory(place.getTypes());
            placeData.put("category", category);
            placesData.add(placeData);
        }
        return placesData;
    }

    public static double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
            final double R = 3958.8; // Radius of the Earth in miles
            double rlat1 = Math.toRadians(lat1); // Convert degrees to radians
            double rlat2 = Math.toRadians(lat2); // Convert degrees to radians
            double difflat = rlat2 - rlat1; // Radian difference (latitudes)
            double difflon = Math.toRadians(lon2 - lon1); // Radian difference (longitudes)

            double a = Math.sin(difflat / 2) * Math.sin(difflat / 2) +
                    Math.cos(rlat1) * Math.cos(rlat2) *
                            Math.sin(difflon / 2) * Math.sin(difflon / 2);
            double c = 2 * Math.asin(Math.sqrt(a));
            return R * c; // Distance in miles
    }

    private void addRandomPlace(List<Place> places, Location location, String category, String mainCategory, Random random) throws Exception {
        List<Place> placeList = apiService.getPlacesApiResponse(location, category).getPlaces();
        if (placeList != null) {
            for (Place place : placeList) {
                Place randomPlace = placeList.get(random.nextInt(placeList.size()));
                if (!places.contains(randomPlace)) {
                    randomPlace.setMainCategory(mainCategory);
                    places.add(randomPlace);
                }
            }
        }
    }

    @GetMapping("/resetMainPlace")
    public void resetMainPlace(HttpSession session) {
        session.invalidate();
    }
}
