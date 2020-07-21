package com.solution.collectionkafka.utils;

/**
 * The Class LoggerMessageUtils has all the messages to be shown during
 * sending the message or for any error occurred.
 */
public class LoggerMessageUtils {

    private LoggerMessageUtils(){}

    public static final String ERROR_INCORRECT_URL_MSG = "Url: {} is not of correct format";
    public static final String CONNECTION_SUCCESS_STATUS_MSG = "Status of the connection is {}";
    public static final String INCOMING_JSON_MSG = "Incoming json meetup json data: {}";
    public static final String INPUT_STREAM_ERROR_MSG = "Some error occurred while read the input stream\"";
    public static final String CONNECTION_FAILURE_MSG = "Some error occurred while opening the HTTP connection for the url: {}";
}
