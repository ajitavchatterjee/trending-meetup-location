package com.solution.meetupreactiveservice.service.impl;

import com.solution.meetupreactiveservice.model.VenueFrequency;
import com.solution.meetupreactiveservice.repository.MeetUpRepository;
import com.solution.meetupreactiveservice.service.MeetUpService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * The Class MeetUpServiceImpl is responsible for providing the implementation
 * of the service methods to interact with Cassandra repository.
 */
@Service
@AllArgsConstructor
public class MeetUpServiceImpl implements MeetUpService {
	private final MeetUpRepository meetUpRepository;

	/**
	 * Fetch meetup venues from repository.
	 *
	 * @return the flux
	 */
	@Override
	public Flux<VenueFrequency> fetchMeetupVenues() {
		return meetUpRepository.findAll();
	}
}
