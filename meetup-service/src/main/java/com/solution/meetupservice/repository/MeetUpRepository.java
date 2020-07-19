package com.solution.meetupservice.repository;

import com.solution.meetupservice.model.VenueFrequency;
import com.solution.meetupservice.model.VenueFrequencyKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetUpRepository extends CassandraRepository<VenueFrequency, VenueFrequencyKey> {
}