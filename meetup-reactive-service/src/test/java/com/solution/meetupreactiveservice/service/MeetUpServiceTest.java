package com.solution.meetupreactiveservice.service;

import com.solution.meetupreactiveservice.model.VenueFrequency;
import com.solution.meetupreactiveservice.model.VenueFrequencyKey;
import com.solution.meetupreactiveservice.repository.MeetUpRepository;
import com.solution.meetupreactiveservice.service.impl.MeetUpServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * The Class MeetUpServiceTest is the basic unit testing for the service method.
 */
@RunWith(MockitoJUnitRunner.class)
public class MeetUpServiceTest {
    private MeetUpServiceImpl meetUpService;
    private Flux<VenueFrequency> venueFrequencyData;

    @Mock
    private MeetUpRepository meetUpRepository;

    @Before
    public void before() {
        meetUpService = new MeetUpServiceImpl(meetUpRepository);
        venueFrequencyData = Flux.just(
            VenueFrequency.builder().count(1).venueFrequencyKey(VenueFrequencyKey.builder().venueId(1).venueName("London").lat(14.89).lon(78.06).build()).build(),
            VenueFrequency.builder().count(8).venueFrequencyKey(VenueFrequencyKey.builder().venueId(1).venueName("Germany").lat(57.35).lon(19.64).build()).build(),
            VenueFrequency.builder().count(10).venueFrequencyKey(VenueFrequencyKey.builder().venueId(1).venueName("Amsterdam").lat(84.27).lon(46.76).build()).build(),
            VenueFrequency.builder().count(14).venueFrequencyKey(VenueFrequencyKey.builder().venueId(1).venueName("Italy").lat(-24.05).lon(8.95).build()).build(),
            VenueFrequency.builder().count(4).venueFrequencyKey(VenueFrequencyKey.builder().venueId(1).venueName("India").lat(29.48).lon(29.47).build()).build()
        );
    }

    /**
     * Test find all success with data.
     */
    @Test
    public void test_findAll_success_withData() {
        Mockito.when(meetUpRepository.findAll()).thenReturn(venueFrequencyData);
        Mono<Long> result = meetUpService.fetchMeetupVenues().count();
        StepVerifier
            .create(result)
            .expectNext(5L)
            .verifyComplete();
    }

    /**
     * Test find all success without data.
     */
    @Test
    public void test_findAll_success_withoutData() {
        Mockito.when(meetUpRepository.findAll()).thenReturn(Flux.empty());
        Mono<Long> result = meetUpService.fetchMeetupVenues().count();
        StepVerifier
            .create(result)
            .expectNext(0L)
            .verifyComplete();
    }
}
