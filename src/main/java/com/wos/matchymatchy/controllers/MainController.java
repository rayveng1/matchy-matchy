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
        Random random = new Random();
        ArrayList<Place> places = new ArrayList<>();

        List<Place> restaurantPlaces = apiService.getPlacesApiResponse(location, "restaurant").getPlaces();
        for (Place place : restaurantPlaces) {
            Place randRestaurantPlace = restaurantPlaces.get(random.nextInt(restaurantPlaces.size()));
            if (!places.contains(randRestaurantPlace))
                places.add(randRestaurantPlace);
        }

        List<Place> bankPlaces = apiService.getPlacesApiResponse(location, "bank").getPlaces();
        for (Place place : bankPlaces) {
            Place randBankPlace = bankPlaces.get(random.nextInt(bankPlaces.size()));
            if (!places.contains(randBankPlace))
                places.add(randBankPlace);
        }

        List<Place> carRepairPlaces = apiService.getPlacesApiResponse(location, "car_repair").getPlaces();
        for (Place place : carRepairPlaces) {
            Place randCarRepairPlace = carRepairPlaces.get(random.nextInt(carRepairPlaces.size()));
            if (!places.contains(randCarRepairPlace))
                places.add(randCarRepairPlace);
        }

        List<Place> insuranceAgencyPlaces = apiService.getPlacesApiResponse(location, "insurance_agency").getPlaces();
        for (Place place : insuranceAgencyPlaces) {
            Place randInsuranceAgencyPlace = insuranceAgencyPlaces.get(random.nextInt(insuranceAgencyPlaces.size()));
            if (!places.contains(randInsuranceAgencyPlace))
                places.add(randInsuranceAgencyPlace);
        }

        List<Place> movieTheaterPlaces = apiService.getPlacesApiResponse(location, "movie_theater").getPlaces();
        for (Place place : movieTheaterPlaces) {
            Place randMovieTheaterPlace = movieTheaterPlaces.get(random.nextInt(movieTheaterPlaces.size()));
            if (!places.contains(randMovieTheaterPlace))
                places.add(randMovieTheaterPlace);
        }

        List<Place> travelAgencyPlaces = apiService.getPlacesApiResponse(location, "travel_agency").getPlaces();
        for (Place place : travelAgencyPlaces) {
            Place randTravelAgencyPlace = travelAgencyPlaces.get(random.nextInt(travelAgencyPlaces.size()));
            if (!places.contains(randTravelAgencyPlace))
                places.add(randTravelAgencyPlace);
        }

        List<Place> hotelPlaces = apiService.getPlacesApiResponse(location, "hotel").getPlaces();
        for (Place place : hotelPlaces) {
            Place randHotelPlace = hotelPlaces.get(random.nextInt(hotelPlaces.size()));
            if (!places.contains(randHotelPlace))
                places.add(randHotelPlace);
        }

        List<Place> fitnessCenterPlaces = apiService.getPlacesApiResponse(location, "fitness_center").getPlaces();
        for (Place place : fitnessCenterPlaces) {
            Place randFitnessCenterPlace = fitnessCenterPlaces.get(random.nextInt(fitnessCenterPlaces.size()));
            if (!places.contains(randFitnessCenterPlace))
                places.add(randFitnessCenterPlace);
        }

        List<Place> amusementParkPlaces = apiService.getPlacesApiResponse(location, "amusement_park").getPlaces();
        for (Place place : amusementParkPlaces) {
            Place randAmusementParkPlace = amusementParkPlaces.get(random.nextInt(amusementParkPlaces.size()));
            if (!places.contains(randAmusementParkPlace))
                places.add(randAmusementParkPlace);
        }

        List<Place> departmentStorePlaces = apiService.getPlacesApiResponse(location, "department_store").getPlaces();
        for (Place place : departmentStorePlaces) {
            Place randDepartmentStorePlace = departmentStorePlaces.get(random.nextInt(departmentStorePlaces.size()));
            if (!places.contains(randDepartmentStorePlace))
                places.add(randDepartmentStorePlace);
        }

        JSONObject jsObject = new JSONObject();

        DecimalFormat df = new DecimalFormat("#.#");
        for (Place place : places) {
            place.setDistance(df.format(haversineDistance((Double) session.getAttribute("latitude"), (Double) session.getAttribute("longitude"), place.getLocation().getLatitude(), place.getLocation().getLongitude())));
            for (Photo photo : place.getPhotos()){
                place.setImageGetRequest(apiService.getPhotosApiResponse(photo.getName()));
            }
        }

        model.addAttribute("jsObject", jsObject);

        model.addAttribute("places", places);
        model.addAttribute("categories", getCategorizedPlaces(places, jsObject));

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

    public static String getMainCategory(List<String> categories) {
        for (String category : categories) {
//            System.out.println("Category: " + category);
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


    public HashMap<String, List<Place>> getCategorizedPlaces(List<Place> places, JSONObject jsObject) {
        HashMap<String, List<Place>> hm = new HashMap<>();
        List<Double> longitudeList = new ArrayList<>();
        List<Double> latitudeList = new ArrayList<>();
        List<String> categoryList = new ArrayList<>();



        for (Place place : places){
            System.out.println("test - "+ place.getEditorialSummary().getText());


//            System.out.println("Place: " + place.getDisplayName().getText() + ", Types: " + place.getTypes());


            //longitudeList.add(place.getLocation().getLongitude());
            //latitudeList.add(place.getLocation().getLatitude());

            String category = getMainCategory(place.getTypes());
            categoryList.add(category);
            if (!hm.containsKey(category)){
                hm.put(category, new ArrayList<>(){{add(place);}});
            } else {
                hm.get(category).add(place);
            }
        }

//         jsObject.put("longitudeList", longitudeList);

//         jsObject.put("latitudeList", latitudeList);

//         jsObject.put("categoryList", categoryList);


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

        List<Place> restaurantPlaces = apiService.getPlacesApiResponse(location, "restaurant").getPlaces();
        if (restaurantPlaces != null)
            places.addAll(restaurantPlaces);

        List<Place> bankPlaces = apiService.getPlacesApiResponse(location, "bank").getPlaces();
        if (bankPlaces != null) {
            places.addAll(bankPlaces);
        }

        List<Place> carRepairPlaces = apiService.getPlacesApiResponse(location, "car_repair").getPlaces();
        if (carRepairPlaces != null) {
            places.addAll(carRepairPlaces);
        }

        List<Place> insuranceAgencyPlaces = apiService.getPlacesApiResponse(location, "insurance_agency").getPlaces();
        if (insuranceAgencyPlaces != null) {
            places.addAll(insuranceAgencyPlaces);
        }

        List<Place> movieTheaterPlaces = apiService.getPlacesApiResponse(location, "movie_theater").getPlaces();
        if (movieTheaterPlaces != null) {
            places.addAll(movieTheaterPlaces);
        }

        List<Place> travelAgencyPlaces = apiService.getPlacesApiResponse(location, "travel_agency").getPlaces();
        if (travelAgencyPlaces != null) {
            places.addAll(travelAgencyPlaces);
        }

        List<Place> hotelPlaces = apiService.getPlacesApiResponse(location, "hotel").getPlaces();
        if (hotelPlaces != null) {
            places.addAll(hotelPlaces);
        }

        List<Place> fitnessCenterPlaces = apiService.getPlacesApiResponse(location, "fitness_center").getPlaces();
        if (fitnessCenterPlaces != null) {
            places.addAll(fitnessCenterPlaces);
        }

        List<Place> amusementParkPlaces = apiService.getPlacesApiResponse(location, "amusement_park").getPlaces();
        if (amusementParkPlaces != null) {
            places.addAll(amusementParkPlaces);
        }

        List<Place> departmentStorePlaces = apiService.getPlacesApiResponse(location, "department_store").getPlaces();
        if (departmentStorePlaces != null) {
            places.addAll(departmentStorePlaces);
        }


        List<Map<String, Object>> placesData = new ArrayList<>();
        for (Place place : places) {
            Map<String, Object> placeData = new HashMap<>();
            placeData.put("longitude", place.getLocation().getLongitude());
            placeData.put("latitude", place.getLocation().getLatitude());
            String category = getMainCategory(place.getTypes());
            placeData.put("category", category);
            placesData.add(placeData);

            System.out.println("Place: " + place.getDisplayName().getText() + ", Category: " + place.getMainCategory());

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


}
