package com.solution.meetupreactiveservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Class MeetupReactiveServiceApplication is responsible for loading the
 * context, configuration to start the reactive application to fetch data from
 * Cassandra database and expose it as a rest end point.
 */
@SpringBootApplication
public class MeetupReactiveServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetupReactiveServiceApplication.class, args);
	}

}
