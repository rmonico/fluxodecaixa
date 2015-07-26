package zero.fluxodecaixa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class CommandLineParserTests {

    @Test
    public void should_parse_conta_add_command_line() {
        CommandLineParser parser = new CommandLineParser(new String[] { "conta", "add", "nova conta", "contabilizavel" });

        parser.run();

        Command c = parser.getCommand();

        assertEquals(ContaAddCommand.class, c.getClass());

        ContaAddCommand command = (ContaAddCommand) c;

        assertEquals("nova conta", command.getNome());
        assertTrue(command.isContabilizavel());
    }

    @Test
    public void should_invalid_command_line_return_error() {
        CommandLineParser parser = new CommandLineParser(new String[] { "invalid module name", "command", "line" });

        parser.run();

        assertNull(parser.getCommand());

        List<String> errors = parser.getErrors();
        assertEquals(1, errors.size());
        assertEquals("Invalid module: \"invalid module name\"", errors.get(0));
    }
}
