package com.solution.sparkkafkaanalyzer.model;

import lombok.Data;

@Data
public class Venue {
    private String venue_name;
    private Double lon;
    private Double lat;
    private Integer venue_id;
}
