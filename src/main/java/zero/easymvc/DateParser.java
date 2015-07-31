package zero.easymvc;

public class DateParser extends TimestampParser {

    @Override
    protected String getDatePattern() {
        return "dd/MMM/yyyy";
    }

}
