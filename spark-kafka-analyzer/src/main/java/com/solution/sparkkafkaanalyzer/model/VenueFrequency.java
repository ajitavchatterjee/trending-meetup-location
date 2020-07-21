package com.solution.sparkkafkaanalyzer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * This class is the model for storing the data into the database.
 *
 */
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
