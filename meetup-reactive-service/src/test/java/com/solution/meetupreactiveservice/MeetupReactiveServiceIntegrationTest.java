package com.solution.meetupreactiveservice;

import com.solution.meetupreactiveservice.repository.MeetUpRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(value = "test")
class MeetupReactiveServiceIntegrationTest {

	@Autowired
	private MeetUpRepository repository;

//	@Test
	public void testFindAll_with_ExistingVenueFrequency() {
		Mono<Long> findAllExisting = repository.findAll()
			.doOnNext(System.out::println)
			.last()
			.flatMap(v -> repository.count())
			.doOnNext(System.out::println);

		StepVerifier
			.create(findAllExisting)
			.expectNext(7L)
			.verifyComplete();
	}

}
