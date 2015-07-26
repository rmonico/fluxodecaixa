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
    public void should_empty_command_line_return_error() {
        initializeParser(new String[] {});

        assertNull(parser.getCommand());

        List<String> errors = parser.getErrors();
        assertEquals(1, errors.size());
        assertEquals("Empty command line", errors.get(0));
    }

    @Test
    public void should_invalid_module_return_error() {
        initializeParser(new String[] { "invalid module name", "command", "line" });

        assertNull(parser.getCommand());

        List<String> errors = parser.getErrors();
        assertEquals(1, errors.size());
        assertEquals("Invalid module: \"invalid module name\"", errors.get(0));
    }

    @Test
    public void should_empty_conta_return_error() {
        initializeParser(new String[] { "conta" });

        assertNull(parser.getCommand());

        List<String> errors = parser.getErrors();
        assertEquals(1, errors.size());
        assertEquals("\"conta\" no command", errors.get(0));
    }

    @Test
    public void should_empty_conta_add_return_error() {
        initializeParser(new String[] { "conta", "add" });

        assertNull(parser.getCommand());

        List<String> errors = parser.getErrors();
        assertEquals(1, errors.size());
        assertEquals("\"conta add\" no argument", errors.get(0));
    }

    @Test
    public void should_parse_conta_add() {
        initializeParser(new String[] { "conta", "add", "nova conta", "contabilizavel" });

        Command c = parser.getCommand();

        assertEquals(ContaAddCommand.class, c.getClass());

        ContaAddCommand command = (ContaAddCommand) c;

        assertEquals("nova conta", command.getNome());
        assertTrue(command.isContabilizavel());
    }

    @Test
    public void should_parse_conta_add_extra_arguments() {
        initializeParser(new String[] { "conta", "add", "nova conta", "contabilizavel", "extra", "argument" });

        assertNull(parser.getCommand());

        List<String> errors = parser.getErrors();
        assertEquals(1, errors.size());
        assertEquals("\"conta add\" extra argument(s)", errors.get(0));
    }

    @Test
    public void should_parse_conta_ls() {
        initializeParser(new String[] { "conta", "ls" });

        assertEquals(ContaLsCommand.class, parser.getCommand().getClass());
    }

    @Test
    public void should_parse_conta_ls_extra_arguments() {
        initializeParser(new String[] { "conta", "ls", "extra", "argument" });


        assertNull(parser.getCommand());

        List<String> errors = parser.getErrors();
        assertEquals(1, errors.size());
        assertEquals("\"conta ls\" extra argument(s)", errors.get(0));
    }
}
