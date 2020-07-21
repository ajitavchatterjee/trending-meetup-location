package com.solution.collectionkafka.producer;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

/**
 * The class LogMemoryAppender is responsible to perform operations to get the
 * required logger messages out of the log appender.
 *
 */
public class LogMemoryAppender extends ListAppender<ILoggingEvent> {

	/**
	 * Reset.
	 */
	public void reset() {
		this.list.clear();
	}

	/**
	 * Contains.
	 *
	 * @param string the string
	 * @param level  the level
	 * @return true, if successful
	 */
	public boolean contains(String string, Level level) {
		return this.list.stream()
				.anyMatch(event -> event.getMessage().toString().contains(string) && event.getLevel().equals(level));
	}

	/**
	 * Count events for logger.
	 *
	 * @param loggerName the logger name
	 * @return the int
	 */
	public int countEventsForLogger(String loggerName) {
		return (int) this.list.stream().filter(event -> event.getLoggerName().contains(loggerName)).count();
	}

	/**
	 * Search.
	 *
	 * @param string the string
	 * @return the list
	 */
	public List<ILoggingEvent> search(String string) {
		return this.list.stream().filter(event -> event.getMessage().toString().contains(string))
				.collect(Collectors.toList());
	}

	/**
	 * Search.
	 *
	 * @param string the string
	 * @param level  the level
	 * @return the list
	 */
	public List<ILoggingEvent> search(String string, Level level) {
		String in = string;
		this.list.stream().forEach(event -> {
			String msg = event.getMessage();
			boolean res = msg.contains(in);
			Level found = event.getLevel();
		});
		return this.list.stream().filter(event -> event.getMessage().contains(string) && event.getLevel().equals(level))
				.collect(Collectors.toList());
	}

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public int getSize() {
		return this.list.size();
	}

	/**
	 * Gets the logged events.
	 *
	 * @return the logged events
	 */
	public List<ILoggingEvent> getLoggedEvents() {
		return Collections.unmodifiableList(this.list);
	}
}
