package zero.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeUtils {

    private static SimpleDateFormat timestampFormatter = new SimpleDateFormat("dd/MMM/yyyy kk:mm:ss.SSS");
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MMM/yyyy");
    private static SimpleDateFormat timeFormatter = new SimpleDateFormat("kk:mm:ss.SSS");

    public static String timestampToString(Calendar timestamp) {
        return timestampFormatter.format(timestamp.getTime());
    }

    public static Calendar stringToTimestamp(String timestamp) throws ParseException {
        return parseString(timestampFormatter, timestamp);
    }

    public static String dateToString(Calendar date) {
        return dateFormatter.format(date.getTime());
    }

    public static Calendar stringToDate(String date) throws ParseException {
        return parseString(dateFormatter, date);
    }

    public static String timeToString(Calendar time) {
        return timeFormatter.format(time.getTime());
    }

    public static Calendar stringToTime(String time) throws ParseException {
        return parseString(timeFormatter, time);
    }

    private static Calendar parseString(DateFormat formatter, String dateString) throws ParseException {
        Calendar calendar = GregorianCalendar.getInstance();

        calendar.setTime(formatter.parse(dateString));

        return calendar;
    }

}
