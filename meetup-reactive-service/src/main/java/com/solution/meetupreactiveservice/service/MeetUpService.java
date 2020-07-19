package com.solution.meetupreactiveservice.service;

import com.solution.meetupreactiveservice.model.VenueFrequency;
import reactor.core.publisher.Flux;

public interface MeetUpService {
    Flux<VenueFrequency> popularVenueMeetups() ;
}
