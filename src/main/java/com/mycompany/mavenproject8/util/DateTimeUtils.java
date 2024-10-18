/**
 * Diese Klasse wird verwendet, um das aktuelle Datum und die Uhrzeit in einem
 * bestimmten Format zu erhalten. 
 * Datum und Uhrzeit: jjjj-MM-tt HH:mm 
 * Datum: jjjj-MM-tt 
 * Wir haben die obige Abbildung verwendet, um zwei verschiedene Daten zu erhalten
 *
 * @author erkan
 */
package com.mycompany.mavenproject8.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DateTimeUtils {

    public static String getTimeFormat() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(formatter);
    }
    
    public static String getDateFormat() {
        LocalDateTime date = LocalDateTime.now(); // Sadece tarih
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
}
