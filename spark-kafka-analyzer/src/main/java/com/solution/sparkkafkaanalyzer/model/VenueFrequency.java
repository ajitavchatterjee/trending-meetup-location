package com.solution.sparkkafkaanalyzer.model;

//CREATE TABLE meetupfrequenncy (venue_id int PRIMARY KEY, venue_name text, lat double, long double, count int);

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class VenueFrequency {
    private int venue_id;
    private String venue_name;
    private double lat;
    private double lon;
    private int count;
}
