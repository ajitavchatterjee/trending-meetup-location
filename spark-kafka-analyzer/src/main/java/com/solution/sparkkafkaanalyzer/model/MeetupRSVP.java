package com.solution.sparkkafkaanalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Instantiates a new meetup RSVP which is the base model for the type of meetup
 * data.
 */
@Getter
@Setter
public class MeetupRSVP {
	@JsonProperty("venue")
	private Venue venue;

	@JsonProperty("visibility")
	private String visibility;

	@JsonProperty("response")
	private String response;

	@JsonProperty("guests")
	private Integer guests;

	@JsonProperty("member")
	private Member member;

	@JsonProperty("rsvp_id")
	private Integer rsvpId;

	@JsonProperty("mtime")
	private Long mTime;

	@JsonProperty("mtime")
	private Event event;

	@JsonProperty("group")
	private Group group;
}
