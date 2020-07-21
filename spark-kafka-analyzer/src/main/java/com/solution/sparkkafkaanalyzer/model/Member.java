package com.solution.sparkkafkaanalyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Instantiates a new member.
 */
@Getter
@Setter
public class Member {
    @JsonProperty("member_id")
    private Integer memberId;

    @JsonProperty("photo")
    private String photo;

    @JsonProperty("member_name")
    private String memberName;

    @JsonProperty("other_services")
    @JsonIgnore
    private String otherServices;
}
