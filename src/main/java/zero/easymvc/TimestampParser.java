package zero.easymvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimestampParser implements BeanParser {

    @Override
    public Calendar parse(Object value) throws BeanParserException {
        // Just support string values for now
        String stringParam = BuiltinParsers.convertToString(value);

        SimpleDateFormat formatter = new SimpleDateFormat(getDatePattern());

        Date date;
        try {
            date = formatter.parse(stringParam);
        } catch (ParseException e) {
            throw new BeanParserException(e);
        }

        Calendar calendar = GregorianCalendar.getInstance();

        calendar.setTime(date);

        return calendar;
    }

    protected String getDatePattern() {
        return "yyyy-MMM-dd kk:mm:ss.SSS";
    }
}
