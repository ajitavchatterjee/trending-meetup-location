package com.solution.sparkkafkaanalyzer.encoder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.serializer.Decoder;
import kafka.serializer.Encoder;

import java.io.IOException;

public class MeetUpRSVPEncoder<MeetUpRSVP> implements Encoder<MeetUpRSVP>, Decoder<MeetUpRSVP> {

    Class<MeetUpRSVP> clazz;

    @Override
    public byte[] toBytes(MeetUpRSVP arg0) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(arg0).getBytes();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public MeetUpRSVP fromBytes(byte[] bytes) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(bytes, clazz);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
