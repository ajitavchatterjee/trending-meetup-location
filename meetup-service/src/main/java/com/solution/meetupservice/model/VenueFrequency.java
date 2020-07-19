package com.solution.meetupservice.model;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Table("meetupfrequenncy")
public class VenueFrequency {
    @PrimaryKey
    private VenueFrequencyKey venueFrequencyKey;
    private int count;
}
