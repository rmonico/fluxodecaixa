package zero.listprinter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateFormatter implements Formatter {

    private static DateFormatter instance;

    public static DateFormatter getInstance() {
        if (instance == null)
            instance = new DateFormatter();

        return instance;
    }

    DateFormat formatter = new SimpleDateFormat("dd/MMM");

    @Override
    public String format(Object data) {
        return formatter.format(data);
    }

}
