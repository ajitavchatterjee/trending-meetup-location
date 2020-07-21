package com.solution.sparkkafkaanalyzer.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Instantiates a new group.
 */
@Getter
@Setter
public class Group {

    private List<GroupTopic> group_topics = null;
    private String group_city;
    private String group_country;
    private Integer group_id;
    private String group_name;
    private Double group_lon;
    private String group_urlname;
    private String group_state;
    private Double group_lat;
}
