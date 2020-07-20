package com.solution.sparkkafkaanalyzer.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solution.sparkkafkaanalyzer.model.MeetupRSVP;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class CustomMeetupRSVPDeserializer implements Deserializer<MeetupRSVP> {
    private static final Logger logger = LoggerFactory.getLogger(CustomMeetupRSVPDeserializer.class);

    @Override
    public void configure(Map configs, boolean isKey) { }

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
        } catch (IOException e) {
            logger.error("Some issue occurred while deserializing the MeetupRSVP data: {}", data);
        }
        return mRSVP;
    }

    @Override
    public void close() { }
}
