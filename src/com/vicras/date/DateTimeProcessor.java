package com.vicras.date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeProcessor implements DateTimeProvider {

    private final DateTimeFormatter dateFormatter;
    private final DateTimeFormatter timeFormatter;

    /**
     * @param dateFormatter format that used to output date
     * @param timeFormatter format that used to output time
     */
    public DateTimeProcessor(DateTimeFormatter dateFormatter, DateTimeFormatter timeFormatter) {
        this.dateFormatter = dateFormatter;
        this.timeFormatter = timeFormatter;
    }

    @Override
    public String getCurrentTime() {
        return LocalDateTime.now()
                .format(timeFormatter);
    }

    @Override
    public String getCurrentDate() {
        return LocalDateTime.now()
                .format(dateFormatter);
    }
}
