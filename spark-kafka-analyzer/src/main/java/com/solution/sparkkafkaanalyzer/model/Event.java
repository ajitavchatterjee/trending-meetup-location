package com.solution.sparkkafkaanalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Instantiates a new event.
 */
@Getter
@Setter
public class Event {
    @JsonProperty("event_name")
    private String eventName;

    @JsonProperty("event_id")
    private String eventId;

    @JsonProperty("time")
    private Long time;

    @JsonProperty("event_url")
    private String eventUrl;
}
