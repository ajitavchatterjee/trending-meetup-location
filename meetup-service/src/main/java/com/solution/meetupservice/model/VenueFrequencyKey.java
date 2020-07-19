package com.solution.meetupservice.model;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;

@PrimaryKeyClass
@Data
public class VenueFrequencyKey implements Serializable {
    @PrimaryKeyColumn(name = "venue_id", type = PrimaryKeyType.PARTITIONED)
    private int venue_id;
    @PrimaryKeyColumn(name = "venue_name", type = PrimaryKeyType.PARTITIONED)
    private String venue_name;
    @PrimaryKeyColumn(name = "lat", type = PrimaryKeyType.CLUSTERED)
    private double lat;
    @PrimaryKeyColumn(name = "lon", type = PrimaryKeyType.CLUSTERED)
    private double lon;
}
