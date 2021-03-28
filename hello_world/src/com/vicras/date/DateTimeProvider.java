package com.vicras.date;

public interface DateTimeProvider {
    /**
     * @return current time
     */
    String getCurrentTime();

    /**
     * @return current date
     */
    String getCurrentDate();
}
