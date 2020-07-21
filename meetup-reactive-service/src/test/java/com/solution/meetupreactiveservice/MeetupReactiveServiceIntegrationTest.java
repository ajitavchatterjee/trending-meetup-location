package com.solution.meetupreactiveservice;

import com.solution.meetupreactiveservice.repository.MeetUpRepository;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;

/**
 * The Class MeetupReactiveServiceIntegrationTest is for basic integration testing of
 * reactive flow of the implementation..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(value = "test")
class MeetupReactiveServiceIntegrationTest {
	@Autowired
	private MeetUpRepository repository;

	/**
	 * Test find all with existing venue frequency.
	 */
	@Test
	public void testFindAll_with_ExistingVenueFrequency() {
		Mono<Long> findAllExisting = repository.findAll().count();

		StepVerifier
		.create(findAllExisting)
		.expectNext(0L) // change it as per data insertion using testdata
		.verifyComplete();
	}
}
