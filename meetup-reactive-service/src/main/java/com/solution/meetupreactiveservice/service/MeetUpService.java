package com.solution.meetupreactiveservice.service;

import com.solution.meetupreactiveservice.model.VenueFrequency;
import reactor.core.publisher.Flux;

/**
 * The Interface MeetUpService which contains the methods to fetch the venue
 * frequency data from database.
 */
public interface MeetUpService {

	/**
	 * Fetch meetup venues.
	 *
	 * @return the flux
	 */
	Flux<VenueFrequency> fetchMeetupVenues();
}
