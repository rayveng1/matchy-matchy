package com.wos.matchymatchy.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentOpeningHours {
    @JsonProperty("weekdayDescriptions")
    private List<String> weekdayDescriptions;

    public CurrentOpeningHours(List<String> weekdayDescriptions) {
        this.weekdayDescriptions = weekdayDescriptions;
    }
}
