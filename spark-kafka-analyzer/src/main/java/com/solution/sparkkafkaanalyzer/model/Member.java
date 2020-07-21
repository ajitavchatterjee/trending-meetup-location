package com.solution.sparkkafkaanalyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * Instantiates a new member.
 */
@Data
public class Member {
    
    private Integer member_id;
    private String photo;
    private String member_name;
    @JsonIgnore
    private String other_services;
}
