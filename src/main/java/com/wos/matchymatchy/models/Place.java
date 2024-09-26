package com.wos.matchymatchy.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Place {

    @JsonProperty("location")
    private Location location = new Location();

    @JsonProperty("displayName")
    private DisplayName displayName = new DisplayName();

    @JsonProperty("rating")
    private BigDecimal rating = new BigDecimal(0);

    @JsonProperty("googleMapsUri")
    private String googleMapsUri = " . ";

    @JsonProperty("websiteUri")
    private String websiteUri = ".";

    @JsonProperty("types")
    private List<String> types =  new ArrayList<>();

    @JsonProperty("internationalPhoneNumber")
    private String internationalPhoneNumber = ".";

    @JsonProperty("editorialSummary")
    private EditorialSummary editorialSummary = new EditorialSummary();

    @JsonProperty("currentOpeningHours")
    private CurrentOpeningHours currentOpeningHours = new CurrentOpeningHours();

    @JsonProperty("formattedAddress")
    private String formattedAddress = ".";

    @JsonProperty("photos")
    private List<Photo> photos = new ArrayList<>();

    private String mainCategory = " ";

    private String imageGetRequest = ".";

    private String distance;

    private String queryParam;

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

    @Override
    public boolean equals(Object o) {
        boolean retVal = false;

        if (o instanceof Place){
//            System.out.println("this: " + this.googleMapsUri);
//            System.out.println("test: " + ((Place) o).googleMapsUri);
            retVal = ((Place) o).googleMapsUri.equals(this.googleMapsUri);
            if (((Place) o).googleMapsUri.equals(this.googleMapsUri)){
                System.out.println("FAIL HERE:\n\t" + ((Place) o).getDisplayName().getText() + ", " + ((Place) o).getQueryParam() + "\n\t" + this.getDisplayName().getText() + ", " + this.getQueryParam());
            }
        }

        return retVal;
    }
}
