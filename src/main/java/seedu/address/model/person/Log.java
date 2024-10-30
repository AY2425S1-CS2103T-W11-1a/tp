package seedu.address.model.person;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a log entry with a timestamp and a log message.
 */
public class Log {
    public static final String MESSAGE_CONSTRAINTS =
            "Timestamp must be in format of DD-MM-YYYY HH:MM and log messages should not be blank";
    public static final String VALIDATION_REGEX =
            "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\\\d{4}) (0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$";
    private final String logString;
    private final LocalDateTime timestamp;

    /**
     * Constructs a {@code Log} with the specified log message and timestamp.
     *
     * @param logString The log message.
     * @param timestamp The timestamp of the log entry.
     */
    public Log(String logString, LocalDateTime timestamp) {
        this.logString = logString;
        this.timestamp = timestamp;
    }

    public Log(String logEntry) {
        String[] logEntryParts = logEntry.split(" ", 2);
        this.timestamp = LocalDateTime.parse(logEntryParts[0], DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        this.logString = logEntryParts[1];
    }

    public static boolean isValidLog(String test) {
        String[] logEntryParts = test.split(" ", 2);
        if (logEntryParts.length != 2) {
            return false;
        }

        String timestamp = logEntryParts[0];
        String logMessage = logEntryParts[1];

        return timestamp.matches(VALIDATION_REGEX) && !logMessage.isBlank();
    }

    /**
     * Returns the log message.
     *
     * @return The log message.
     */
    public String getLogString() {
        return logString;
    }

    /**
     * Returns the timestamp of the log entry.
     *
     * @return The timestamp of the log entry.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the string representation of the log entry.
     *
     * @return The string representation of the log entry.
     */
    @Override
    public String toString() {
        return timestamp + " " + logString;
    }

    /**
     * Checks if this log entry is equal to another object.
     *
     * @param other The other object to compare to.
     * @return True if the other object is equal to this log entry.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Log)) {
            return false;
        }

        Log otherLog = (Log) other;
        return otherLog.getLogString().equals(getLogString())
                && otherLog.getTimestamp().equals(getTimestamp());
    }

    /**
     * Returns the hash code of the log entry.
     *
     * @return The hash code of the log entry.
     */
    @Override
    public int hashCode() {
        return logString.hashCode() + timestamp.hashCode();
    }
}
