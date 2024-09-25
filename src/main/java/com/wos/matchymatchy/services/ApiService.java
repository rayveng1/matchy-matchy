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

    public ApiResponse getPlacesApiResponse(Location location, String type) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Type", "application/json");
        headers.add("X-Goog-Api-Key", googleApiKey);
        headers.add("X-Goog-FieldMask", "places.displayName,places.rating,places.location,places.websiteUri,places.googleMapsUri,places.types,places.editorialSummary,places.internationalPhoneNumber,places.currentOpeningHours.weekdayDescriptions,places.formattedAddress,places.photos");


        JSONObject centerObject = new JSONObject();
        JSONObject circleObject = new JSONObject();
        JSONObject locationRestrictionObject = new JSONObject();
        JSONObject bodyObject = new JSONObject();

        centerObject.put("latitude", location.getLatitude());

        centerObject.put("longitude", location.getLongitude());

        circleObject.put("center", centerObject);


        Random random = new Random();

        int randRadius = 4000 + random.nextInt(8000);
        circleObject.put("radius", randRadius);

        locationRestrictionObject.put("circle", circleObject);

        bodyObject.put("locationRestriction", locationRestrictionObject);

        bodyObject.put("includedTypes", new ArrayList<String>(Arrays.asList(type)));

        int randCount = 1 + random.nextInt(3);
        bodyObject.put("maxResultCount", randCount);

        HttpEntity<String> entity = new HttpEntity<>(bodyObject.toString(), headers);
        String baseUrl = "https://places.googleapis.com/v1/places:searchNearby";

        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, String.class);

        String jsonResponse = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("BRUHHHHHHHHHH");
        return mapper.readValue(jsonResponse, ApiResponse.class);
    }


    public String getPhotosApiResponse(String idAndReference) {
        String baseUrl = "https://places.googleapis.com/v1/places";

        String[] strings = idAndReference.split("/");
        String endpoint = String.format("%s/%s/photos/%s/media?maxHeightPx=400&maxWidthPx=400&key=%s", baseUrl, strings[1], strings[3], googleApiKey);

        return endpoint;

    }
}
