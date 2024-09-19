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

@Service
public class ApiService {
    RestTemplate restTemplate = new RestTemplate();
    @Value("${google.api.key}")
    private String googleApiKey;

    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ApiResponse getApiResponse(Location location) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Type", "application/json");
        headers.add("X-Goog-Api-Key", googleApiKey);
        headers.add("X-Goog-FieldMask", "places.displayName,places.rating,places.location,places.websiteUri,places.googleMapsUri,places.types,places.editorialSummary,places.internationalPhoneNumber,places.currentOpeningHours.weekdayDescriptions");

        JSONObject centerObject = new JSONObject();
        JSONObject circleObject = new JSONObject();
        JSONObject locationRestrictionObject = new JSONObject();
        JSONObject bodyObject = new JSONObject();
        centerObject.put("latitude", location.getLatitude());
        centerObject.put("longitude", location.getLongitude());
        circleObject.put("center", centerObject);
        circleObject.put("radius", 500);
        locationRestrictionObject.put("circle", circleObject);
        bodyObject.put("locationRestriction", locationRestrictionObject);

        HttpEntity<String> entity = new HttpEntity<>(bodyObject.toString(), headers);
        String baseUrl = "https://places.googleapis.com/v1/places:searchNearby";

        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, String.class);

        String jsonResponse = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(jsonResponse);
        return mapper.readValue(jsonResponse, ApiResponse.class);
    }
}
