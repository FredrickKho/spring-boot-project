package com.fredrick.financial_management.util;

import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
public class HelperUtil {
    public static String convertToISO8601(String dateString) {
        // Parse the input date string with the format "Fri Jan 03 00:12:10 ICT 2025"
        // We'll first convert this string to a ZonedDateTime using a custom formatter.

        // Assuming that you already know the input is in ICT or another valid time zone
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateString, DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy"));

        // Convert to ISO 8601 format with the time zone offset
        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return zonedDateTime.format(isoFormatter); // This will return in ISO 8601 format with the correct offset
    }
}
