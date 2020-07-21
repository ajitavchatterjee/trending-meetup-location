package com.solution.sparkkafkaanalyzer.model;

import lombok.Data;

/**
 * Instantiates a new event.
 */
@Data
public class Event {
    private String event_name;
    private String event_id;
    private Long time;
    private String event_url;
}
