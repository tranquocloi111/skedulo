package logic.utils;

import framework.utils.Log;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;


public class Parser {
    static SimpleDateFormat format;
    public static String parseDateFormate(Date value, String dateFormat){
        try {
            format = new SimpleDateFormat(dateFormat);
            String dateString = format.format(value);
            return  dateString;
        }catch (Exception ex){
            Log.error(ex.getMessage());
        }
        return null;
    }

    public static LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Date asDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }

    public static int asInteger(Object str) {
        return Integer.parseInt(String.valueOf(str));
    }

    public static Float parseToInt(String s){
        return Float.parseFloat(s);
    }

    public static String parseDateTimeFormat(DateTime value, String dateFormat){
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(dateFormat);
            String dateString = formatter.print(value);
            return  dateString;
        }catch (Exception ex){
            Log.error(ex.getMessage());
        }
        return null;
    }

}
