package zero.easymvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimestampParser implements BeanParser {

    private static final int TODAY_INDEX = 1;
    private String[] dateWords = { "ontem", "hoje", "amanhã" };
    private String stringArg;
    private Calendar calendar;

    @Override
    public Calendar parse(Object value) throws BeanParserException {
        // Just support string values for now
        stringArg = BuiltinParsers.convertToString(value);

        calendar = GregorianCalendar.getInstance();

        if (isDateWord()) {
            return calendar;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(getDatePattern());

        Date date;
        try {
            date = formatter.parse(stringArg);
        } catch (ParseException e) {
            throw new BeanParserException(e);
        }

        calendar.setTime(date);

        return calendar;
    }

    private boolean isDateWord() {
        for (int i = 0; i < dateWords.length; i++) {
            if (dateWords[i].equals(stringArg)) {
                calendar.add(Calendar.DAY_OF_MONTH, i - TODAY_INDEX);

                return true;
            }
        }

        return false;
    }

    protected String getDatePattern() {
        return "yyyy-MMM-dd kk:mm:ss.SSS";
    }
}
