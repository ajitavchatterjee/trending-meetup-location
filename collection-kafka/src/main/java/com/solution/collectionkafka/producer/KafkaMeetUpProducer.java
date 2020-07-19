package com.solution.collectionkafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@Component
@EnableBinding(Source.class)
public class KafkaMeetUpProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaMeetUpProducer.class);
    private static final int SENDING_MESSAGE_TIMEOUT_MS = 10000;

    private final Source source;

    public KafkaMeetUpProducer(Source source) {
        this.source = source;
    }

    public void readMeetupJson(){

        HttpURLConnection con = null;
        try {
            URL url = new URL("https://stream.meetup.com/2/rsvps");

            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(50000);
            con.setRequestMethod("GET");
            logger.debug("Status is " + con.getResponseCode());
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                logger.debug("Incoming json "+inputLine);
                source.output()
                    .send(MessageBuilder.withPayload(inputLine).build(),
                        SENDING_MESSAGE_TIMEOUT_MS) ;
            }
            in.close();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(con!= null)
                con.disconnect();
        }
    }
}
