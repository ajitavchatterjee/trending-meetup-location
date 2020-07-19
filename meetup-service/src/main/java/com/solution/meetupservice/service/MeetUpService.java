package com.solution.meetupservice.service;

import com.solution.meetupservice.model.VenueFrequency;

import java.util.List;

public interface MeetUpService {
    List<VenueFrequency> popularVenueMeetups() ;
}
