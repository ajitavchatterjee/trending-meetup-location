package com.solution.sparkkafkaanalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Instantiates a new group.
 */
@Getter
@Setter
public class Group {
    @JsonProperty("group_topics")
    private List<GroupTopic> groupTopics = null;

    @JsonProperty("group_city")
    private String groupCity;

    @JsonProperty("group_country")
    private String groupCountry;

    @JsonProperty("group_id")
    private Integer groupId;

    @JsonProperty("group_name")
    private String groupName;

    @JsonProperty("group_lon")
    private Double groupLon;

    @JsonProperty("group_urlname")
    private String groupUrlName;

    @JsonProperty("group_state")
    private String groupState;

    @JsonProperty("group_lat")
    private Double groupLat;
}
