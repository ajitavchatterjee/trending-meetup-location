package com.solution.meetupreactiveservice.controller;

import com.solution.meetupreactiveservice.model.VenueFrequency;
import com.solution.meetupreactiveservice.service.MeetUpService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Timer;

@RestController
@AllArgsConstructor
public class MeetUpController {

    private final MeetUpService service;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/meetupRsvps", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<VenueFrequency> popularVenueMeetups() {
        return service.popularVenueMeetups();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/test", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Long> test() {

        return Flux.interval(Duration.ofMillis(100));
    }
}
