package zero.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtils {

    private static SimpleDateFormat timestampFormatter = new SimpleDateFormat("dd/MMM/yyyy kk:mm:ss.SSS");
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MMM/yyyy");
    private static SimpleDateFormat timeFormatter = new SimpleDateFormat("kk:mm:ss.SSS");

    public static String timestampToString(Calendar timestamp) {
        return timestampFormatter.format(timestamp.getTime());
    }

    public static String dateToString(Calendar date) {
        return dateFormatter.format(date.getTime());
    }

    public static String timeToString(Calendar time) {
        return timeFormatter.format(time.getTime());
    }

}
