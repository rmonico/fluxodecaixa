package zero.easymvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;

import zero.utils.TimeUtils;

public class DateTimeParsersTests extends AbstractEasyMVCTest {

    public static class MyCommandArguments {
        @PositionalParameter(parser = TimestampParser.class)
        private Calendar timestampParam;

        @PositionalParameter(after = "timestampParam", parser = DateParser.class)
        private Calendar dateParam;

        @PositionalParameter(after = "dateParam", parser = TimeParser.class)
        private Calendar timeParam;

        @PositionalParameter(after = "timeParam", parser = DateParser.class)
        private Calendar yesterdayParam;

        @PositionalParameter(after = "yesterdayParam", parser = DateParser.class)
        private Calendar todayParam;

        @PositionalParameter(after = "todayParam", parser = DateParser.class)
        private Calendar tomorrowParam;
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

        List<Object> beans = controller.run("command", "1984-jun-08 17:40:12.345", "30/jul/2015", "20:40", "ontem", "hoje", "amanhã");

        EasyMVCAssert.assertBeanList(beans, 1);

        MyCommandArguments args = EasyMVCAssert.assertAndGetBean(beans, 0, MyCommandArguments.class);

        assertNotNull(args.timestampParam);
        assertEquals("08/06/1984 17:40:12.345", TimeUtils.timestampToString(args.timestampParam));

        assertNotNull(args.dateParam);
        assertEquals("30/07/2015", TimeUtils.dateToString(args.dateParam));

        assertNotNull(args.timeParam);
        assertEquals("20:40:00.000", TimeUtils.timeToString(args.timeParam));

        assertNotNull(args.yesterdayParam);

        Calendar today = GregorianCalendar.getInstance();

        Calendar yesterday = (Calendar) today.clone();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);

        assertEquals(TimeUtils.dateToString(yesterday), TimeUtils.dateToString(args.yesterdayParam));
        assertEquals(TimeUtils.dateToString(today), TimeUtils.dateToString(args.todayParam));

        Calendar tomorrow = (Calendar) today.clone();
        tomorrow.add(Calendar.DAY_OF_MONTH, +1);

        assertEquals(TimeUtils.dateToString(tomorrow), TimeUtils.dateToString(args.tomorrowParam));
    }

}
