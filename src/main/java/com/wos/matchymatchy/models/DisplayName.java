package com.wos.matchymatchy.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DisplayName {
    @JsonProperty("text")
    private String text;

    @JsonProperty("languageCode")
    private String languageCode;

    public DisplayName(final String text, final String languageCode) {
        this.text = text;
        this.languageCode = languageCode;
    }
}
