package zero.listprinter;

import java.util.Calendar;

public class DateToCalendarConversor implements DataExtractor {

    @Override
    public Object extract(Object data) throws ListPrinterException {
        Calendar cal = (Calendar) data;

        return cal.getTime();
    }

}
