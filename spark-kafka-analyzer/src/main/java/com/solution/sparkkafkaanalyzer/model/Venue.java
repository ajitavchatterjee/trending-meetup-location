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
    private String venue_name;
    private Double lon;
    private Double lat;
    private Integer venue_id;
}
