package com.wos.matchymatchy.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Place {

    @JsonProperty("location")
    private Location location;

    @JsonProperty("displayName")
    private DisplayName displayName;

    @JsonProperty("rating")
    private BigDecimal rating;

    @JsonProperty("googleMapsUri")
    private String googleMapsUri;

    @JsonProperty("websiteUri")
    private String websiteUri;

    @JsonProperty("types")
    private List<String> types;

    @JsonProperty("internationalPhoneNumber")
    private String internationalPhoneNumber;

    @JsonProperty("editorialSummary")
    private EditorialSummary editorialSummary;

    @JsonProperty("currentOpeningHours")
    private CurrentOpeningHours currentOpeningHours;

    @JsonProperty("formattedAddress")
    private String formattedAddress;

    private String mainCategory;

    public Place(Location location, DisplayName displayName, BigDecimal rating, String googleMapsUri, String websiteUri, List<String> types, String internationalPhoneNumber, EditorialSummary editorialSummary, CurrentOpeningHours currentOpeningHours, String formattedAddress) {
        this.location = location;
        this.displayName = displayName;
        this.rating = rating;
        this.googleMapsUri = googleMapsUri;
        this.websiteUri = websiteUri;
        this.types = types;
        this.internationalPhoneNumber = internationalPhoneNumber;
        this.editorialSummary = editorialSummary;
        this.currentOpeningHours = currentOpeningHours;
        this.formattedAddress = formattedAddress;
    }
}
