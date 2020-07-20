package com.solution.collectionkafka.producer;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(value="test")
public class KafkaMeetUpProducerTest {

    @Autowired
    private KafkaMeetUpProducer producer;

    @Autowired
    private MessageCollector messageCollector;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void test_sendMessageToKafka_usingSource_someData() {
        Message<String> message = new GenericMessage<>("Stream data");
        producer.getSource().output().send(MessageBuilder.withPayload(message).build());
        Message<String> received = (Message<String>) messageCollector.forChannel(producer.getSource().output()).poll();
        Assert.assertNotNull(received);
    }

}
