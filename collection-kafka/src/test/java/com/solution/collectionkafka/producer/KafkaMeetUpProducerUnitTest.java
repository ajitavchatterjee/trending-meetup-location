package com.solution.collectionkafka.producer;

import static org.assertj.core.api.Assertions.assertThat;

import com.solution.collectionkafka.config.ConnectionConfig;
import com.solution.collectionkafka.utils.LoggerMessageUtils;
import com.solution.collectionkafka.utils.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;

@RunWith(MockitoJUnitRunner.class)
public class KafkaMeetUpProducerUnitTest {

    private KafkaMeetUpProducer kafkaMeetUpProducer;
    private static LogMemoryAppender logMemoryAppender;

    @Mock
    private ConnectionConfig config;

    @Before
    public void setup() {
        kafkaMeetUpProducer = new KafkaMeetUpProducer(config, () -> (message, timeout) -> true);
        Logger logger = (Logger) LoggerFactory.getLogger(TestUtils.LOGGER_NAME);
        logMemoryAppender = new LogMemoryAppender();
        logMemoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.DEBUG);
        logger.addAppender(logMemoryAppender);
        logMemoryAppender.start();
    }

    @After
    public void cleanUp() {
        logMemoryAppender.reset();
        logMemoryAppender.stop();
    }

    @Test
    public void test_readRSVPMeetupJson_IncorrectUrl() {
        Mockito.when(config.getUrl()).thenReturn(null);
        kafkaMeetUpProducer.readRSVPMeetupJson();
        assertThat(logMemoryAppender.countEventsForLogger(TestUtils.LOGGER_NAME)).isEqualTo(1);
        assertThat(logMemoryAppender.search(LoggerMessageUtils.ERROR_INCORRECT_URL_MSG, Level.ERROR).size()).isEqualTo(1);
    }

    @Test
    public void test_readRSVPMeetupJson_success_logger() {
        Mockito.when(config.getUrl()).thenReturn(TestUtils.DUMMY_URL);
        kafkaMeetUpProducer.readRSVPMeetupJson();
        assertThat(logMemoryAppender.search(LoggerMessageUtils.CONNECTION_SUCCESS_STATUS_MSG, Level.DEBUG).size()).isEqualTo(1);
        assertThat(logMemoryAppender.search(LoggerMessageUtils.INCOMING_JSON_MSG, Level.DEBUG).size()).isGreaterThan(0);
    }
}
