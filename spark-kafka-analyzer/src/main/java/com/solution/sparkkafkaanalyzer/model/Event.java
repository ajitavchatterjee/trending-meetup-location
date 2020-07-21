package com.solution.sparkkafkaanalyzer.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Instantiates a new event.
 */
@Getter
@Setter
public class Event {
    private String event_name;
    private String event_id;
    private Long time;
    private String event_url;
}
