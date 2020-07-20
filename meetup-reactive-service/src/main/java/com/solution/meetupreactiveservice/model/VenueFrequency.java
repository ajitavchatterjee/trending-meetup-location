package com.solution.meetupreactiveservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Table("meetupfrequenncy")
@AllArgsConstructor
public class VenueFrequency {
    @PrimaryKey
    private VenueFrequencyKey venueFrequencyKey;
    private Integer count;
}
