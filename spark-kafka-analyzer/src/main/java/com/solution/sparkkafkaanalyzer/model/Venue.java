package com.solution.sparkkafkaanalyzer.model;

import lombok.Data;

/**
 * Instantiates a new venue.
 */
@Data
public class Venue {
    private String venue_name;
    private Double lon;
    private Double lat;
    private Integer venue_id;
}
