package com.solution.meetupreactiveservice.service.impl;

import com.solution.meetupreactiveservice.model.VenueFrequency;
import com.solution.meetupreactiveservice.repository.MeetUpRepository;
import com.solution.meetupreactiveservice.service.MeetUpService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class MeetUpServiceImpl implements MeetUpService {
    private final MeetUpRepository meetUpRepository;

    @Override
    public Flux<VenueFrequency> popularVenueMeetups() {
        return meetUpRepository.findAll();
    }
}
