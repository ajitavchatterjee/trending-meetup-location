package com.solution.meetupreactiveservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;

/**
 * The Class VenueFrequencyKey is the class that represent multiple primary keys
 * and clustered keys.
 */
@PrimaryKeyClass
@Data
@AllArgsConstructor
@Builder
public class VenueFrequencyKey implements Serializable {
	@PrimaryKeyColumn(name = "venue_id", type = PrimaryKeyType.PARTITIONED)
	private int venueId;
	@PrimaryKeyColumn(name = "venue_name", type = PrimaryKeyType.PARTITIONED)
	private String venueName;
	@PrimaryKeyColumn(name = "lat", type = PrimaryKeyType.CLUSTERED)
	private double lat;
	@PrimaryKeyColumn(name = "lon", type = PrimaryKeyType.CLUSTERED)
	private double lon;
}
