package com.solution.sparkkafkaanalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Instantiates a new venue.
 */
@Getter
@Setter
public class Venue {
    @JsonProperty("venue_name")
    private String venueName;

    @JsonProperty("lon")
    private Double lon;

    @JsonProperty("lat")
    private Double lat;

    @JsonProperty("venue_id")
    private Integer venueId;
}
