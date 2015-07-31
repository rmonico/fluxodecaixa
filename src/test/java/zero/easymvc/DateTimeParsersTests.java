package zero.easymvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;

public class DateTimeParsersTests extends AbstractEasyMVCTest {

    private SimpleDateFormat formatter;

    public static class MyCommandArguments {
        @PositionalParameter(parser = TimestampParser.class)
        private Calendar timestampParam;

        @PositionalParameter(after = "timestampParam", parser = DateParser.class)
        private Calendar dateParam;

        @PositionalParameter(after = "dateParam", parser = TimeParser.class)
        private Calendar timeParam;

    }

    public static class MyCommand {
        @ArgumentsBean
        @Bean
        private MyCommandArguments args;

        @CommandHandler(path = "command")
        public void run() {

        }
    }

    public static class MyRenderer {
        @SuppressWarnings("unused")
        private MyCommandArguments args;

        @Renderer(path = "command")
        public void run() {

        }
    }

    @Test
    public void should_parse_a_time_stamp_argument() throws EasyMVCException {
        controller.registerCommandHandler(MyCommand.class);
        controller.registerRenderer(MyRenderer.class);

        List<Object> beans = controller.run("command", "1984-jun-08 17:40:12.345", "30/jul/2015", "20:40");

        EasyMVCAssert.assertBeanList(beans, 1);

        MyCommandArguments args = EasyMVCAssert.assertAndGetBean(beans, 0, MyCommandArguments.class);

        formatter = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss.SSS");

        assertNotNull(args.timestampParam);
        Calendar expectedTimestamp = GregorianCalendar.getInstance();
        setDateFields(expectedTimestamp, 1984, Calendar.JUNE, 8);
        setTimeFields(expectedTimestamp, 17, 40, 12, 345);
        assertEquals(timeToString(expectedTimestamp), timeToString(args.timestampParam));

        assertNotNull(args.dateParam);
        Calendar expectedDate = GregorianCalendar.getInstance();
        setDateFields(expectedDate, 2015, Calendar.JULY, 30);
        setTimeFields(expectedDate, 0, 0, 0, 0);
        assertEquals(timeToString(expectedDate), timeToString(args.dateParam));

        assertNotNull(args.timeParam);
        Calendar expectedTime = GregorianCalendar.getInstance();
        setDateFields(expectedTime, 1970, Calendar.JANUARY, 1);
        setTimeFields(expectedTime, 20, 40, 0, 0);
        assertEquals(timeToString(expectedTime), timeToString(args.timeParam));

    }

    private void setDateFields(Calendar expectedDate, int year, int month, int day) {
        expectedDate.set(Calendar.YEAR, year);
        expectedDate.set(Calendar.MONTH, month);
        expectedDate.set(Calendar.DAY_OF_MONTH, day);
    }

    private void setTimeFields(Calendar expectedDate, int hour, int minute, int second, int miliseconds) {
        expectedDate.set(Calendar.HOUR_OF_DAY, hour);
        expectedDate.set(Calendar.MINUTE, minute);
        expectedDate.set(Calendar.SECOND, second);
        expectedDate.set(Calendar.MILLISECOND, miliseconds);
    }

    private String timeToString(Calendar expectedTimestamp) {
        return formatter.format(expectedTimestamp.getTime());
    }

}
