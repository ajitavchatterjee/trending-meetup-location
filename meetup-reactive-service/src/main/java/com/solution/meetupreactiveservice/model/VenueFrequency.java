package com.solution.meetupreactiveservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * Instantiates a new venue frequency model class which represents a table in
 * the cassandra database.
 */
@Data
@Table("venuefrequency")
@AllArgsConstructor
@Builder
public class VenueFrequency {
	@PrimaryKey
	private VenueFrequencyKey venueFrequencyKey;
	private Integer count;
}
