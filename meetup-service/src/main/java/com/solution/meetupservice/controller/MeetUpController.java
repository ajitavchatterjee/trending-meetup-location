package com.solution.meetupservice.controller;

import com.solution.meetupservice.model.VenueFrequency;
import com.solution.meetupservice.service.MeetUpService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class MeetUpController {

    private final MeetUpService service;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/meetupRsvps")
    public @ResponseBody
    ResponseEntity<List<VenueFrequency>> popularVenueMeetups() {
        return ResponseEntity.ok(service.popularVenueMeetups());
    }

}
