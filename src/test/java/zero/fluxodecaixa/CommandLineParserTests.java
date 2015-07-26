package zero.fluxodecaixa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CommandLineParserTests {

    @Test
    public void should_parse_command_line() {
        CommandLineParser parser = new CommandLineParser(new String[] { "conta", "add", "nova conta", "contabilizavel" });

        parser.run();

        Command c = parser.getCommand();

        assertEquals(ContaAddCommand.class, c.getClass());

        ContaAddCommand command = (ContaAddCommand) c;

        assertEquals("nova conta", command.getNome());
        assertTrue(command.isContabilizavel());
    }
}
