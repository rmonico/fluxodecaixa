package zero.fluxodecaixa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class CommandLineParserTests {

    private CommandLineParser parser;

    private CommandLineParser initializeParser(String[] args) {
        parser = new CommandLineParser(args);

        parser.run();

        return parser;
    }

    @Test
    public void should_parse_conta_add_command_line() {
        initializeParser(new String[] { "conta", "add", "nova conta", "contabilizavel" });

        Command c = parser.getCommand();

        assertEquals(ContaAddCommand.class, c.getClass());

        ContaAddCommand command = (ContaAddCommand) c;

        assertEquals("nova conta", command.getNome());
        assertTrue(command.isContabilizavel());
    }

    @Test
    public void should_invalid_command_line_return_error() {
        initializeParser(new String[] { "invalid module name", "command", "line" });

        assertNull(parser.getCommand());

        List<String> errors = parser.getErrors();
        assertEquals(1, errors.size());
        assertEquals("Invalid module: \"invalid module name\"", errors.get(0));
    }

}
