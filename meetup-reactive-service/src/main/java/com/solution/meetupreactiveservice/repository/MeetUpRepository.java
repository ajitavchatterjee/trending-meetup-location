package com.solution.meetupreactiveservice.repository;

import com.solution.meetupreactiveservice.model.VenueFrequency;
import com.solution.meetupreactiveservice.model.VenueFrequencyKey;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetUpRepository extends ReactiveCassandraRepository<VenueFrequency, VenueFrequencyKey> {
}