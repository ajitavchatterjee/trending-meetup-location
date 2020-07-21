package com.solution.sparkkafkaanalyzer.model;

import lombok.Data;

/**
 * Instantiates a new meetup RSVP which is the base model for the type of meetup
 * data.
 */
@Data
public class MeetupRSVP {
	private Venue venue;
	private String visibility;
	private String response;
	private Integer guests;
	private Member member;
	private Integer rsvp_id;
	private Long mtime;
	private Event event;
	private Group group;
}
