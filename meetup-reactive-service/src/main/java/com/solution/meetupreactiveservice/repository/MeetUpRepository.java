package com.solution.meetupreactiveservice.repository;

import com.solution.meetupreactiveservice.model.VenueFrequency;
import com.solution.meetupreactiveservice.model.VenueFrequencyKey;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * The Interface MeetUpRepository provides methods to interact with the
 * Cassandra database in a reactive way.
 */
@Repository
public interface MeetUpRepository extends ReactiveCassandraRepository<VenueFrequency, VenueFrequencyKey> {
}