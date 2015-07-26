package zero.fluxodecaixa;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CommandLineParserTests {

    @Test
    public void should_parse_command_line() {
        CommandLineParser parser = new CommandLineParser(new String[] {"conta add nova_conta"});
        
        parser.run();
        
        Command command = parser.getCommand();
        
        assertEquals(Command.CONTA_ADD, command);
    }
}
