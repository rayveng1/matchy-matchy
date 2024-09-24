package com.wos.matchymatchy.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wos.matchymatchy.models.ApiResponse;
import com.wos.matchymatchy.models.Location;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@Service
public class ApiService {
    RestTemplate restTemplate = new RestTemplate();
    @Value("${google.api.key}")
    private String googleApiKey;

    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ApiResponse getApiResponse(Location location, String type) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Type", "application/json");
        headers.add("X-Goog-Api-Key", googleApiKey);
        headers.add("X-Goog-FieldMask", "places.displayName,places.rating,places.location,places.websiteUri,places.googleMapsUri,places.types,places.editorialSummary,places.internationalPhoneNumber,places.currentOpeningHours.weekdayDescriptions,places.formattedAddress");


        JSONObject centerObject = new JSONObject();
        JSONObject circleObject = new JSONObject();
        JSONObject locationRestrictionObject = new JSONObject();
        JSONObject bodyObject = new JSONObject();

        centerObject.put("latitude", location.getLatitude());

        centerObject.put("longitude", location.getLongitude());

        circleObject.put("center", centerObject);
        Random random = new Random();
        int randRadius = 8000 + random.nextInt(24000);
        System.out.println(randRadius);
        circleObject.put("radius", randRadius);

        locationRestrictionObject.put("circle", circleObject);


        bodyObject.put("locationRestriction", locationRestrictionObject);

//        ArrayList<String> placeTypes = new ArrayList<String>(Arrays.asList(type));
//        String randType = placeTypes.get(random.nextInt(placeTypes.size()));
//        System.out.println(randType);
        bodyObject.put("includedTypes", new ArrayList<String>(Arrays.asList(type)));

        int randCount = 1 + random.nextInt(20);
        bodyObject.put("maxResultCount", randCount);

        HttpEntity<String> entity = new HttpEntity<>(bodyObject.toString(), headers);
        String baseUrl = "https://places.googleapis.com/v1/places:searchNearby";

        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, String.class);

        String jsonResponse = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(jsonResponse);
        return mapper.readValue(jsonResponse, ApiResponse.class);
    }
}
