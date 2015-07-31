package zero.easymvc;

public class TimeParser extends TimestampParser {

    @Override
    protected String getDatePattern() {
        return "kk:mm";
    }
}
