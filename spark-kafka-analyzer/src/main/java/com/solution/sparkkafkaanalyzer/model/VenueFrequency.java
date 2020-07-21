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
	private int venueId;
	private String venueName;
	private double lat;
	private double lon;
	private int count;
}
