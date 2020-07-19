package com.solution.sparkkafkaanalyzer.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solution.sparkkafkaanalyzer.model.MeetupRSVP;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomSerializer implements Serializer<MeetupRSVP> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String s, MeetupRSVP meetupRSVP) {
        byte[] value = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            value = objectMapper.writeValueAsString(s).getBytes();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public byte[] serialize(String topic, Headers headers, MeetupRSVP data) {
        return new byte[0];
    }

    @Override
    public void close() {

    }
}
