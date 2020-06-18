package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T lt, T startTime, T endTime) {
        boolean result = startTime == null || lt.compareTo(startTime) >= 0;
        result = endTime != null ? result && (lt.compareTo(endTime) < 0) : result;
        return result;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate strToDate(String s) {
        return (s == null) || (s.isEmpty()) ? null : LocalDate.parse(s);
    }

    public static LocalTime strToTime(String s) {
        return (s == null) || (s.isEmpty()) ? null : LocalTime.parse(s);
    }
}

