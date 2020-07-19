package com.solution.sparkkafkaanalyzer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solution.sparkkafkaanalyzer.model.MeetupRSVP;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomDeserializer implements Deserializer<MeetupRSVP> {
    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public MeetupRSVP deserialize(String s, byte[] bytes) {
        return null;
    }

    @Override
    public MeetupRSVP deserialize(String topic, Headers headers, byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        MeetupRSVP mRSVP = null;
        try {
            mRSVP = mapper.readValue(data, MeetupRSVP.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRSVP;
    }

    @Override
    public void close() {

    }
}
