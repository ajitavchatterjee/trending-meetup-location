package com.solution.sparkkafkaanalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Instantiates a new group topic.
 */
@Getter
@Setter
public class GroupTopic {
    @JsonProperty("urlkey")
    private String urlKey;

    @JsonProperty("topic_name")
    private String topicName;
}
