package com.solution.meetupservice.service.impl;

import com.solution.meetupservice.model.VenueFrequency;
import com.solution.meetupservice.repository.MeetUpRepository;
import com.solution.meetupservice.service.MeetUpService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MeetUpServiceImpl implements MeetUpService {
    private final MeetUpRepository meetUpRepository;

    @Override
    public List<VenueFrequency> popularVenueMeetups() {
        return meetUpRepository.findAll();
    }
}
