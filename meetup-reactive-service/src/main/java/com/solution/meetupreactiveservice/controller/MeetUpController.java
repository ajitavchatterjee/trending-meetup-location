package com.solution.meetupreactiveservice.controller;

import com.solution.meetupreactiveservice.model.VenueFrequency;
import com.solution.meetupreactiveservice.service.MeetUpService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * The Class MeetUpController is the class to create the rest endpoints to fetch
 * all the venue frequency data from Cassandra database.
 */
@RestController
@AllArgsConstructor
public class MeetUpController {

	private final MeetUpService service;

	/**
	 * Fetch meetup venue frequncy and exposes it as a endpoint to the frontend.
	 *
	 * @return the flux
	 */
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "/meetupVenues", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<VenueFrequency> fetchMeetupVenues() {
		return service.fetchMeetupVenues();
	}

}
