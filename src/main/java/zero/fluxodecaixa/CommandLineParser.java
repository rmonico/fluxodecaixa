package zero.fluxodecaixa;

import java.util.List;

public class CommandLineParser {

    private String[] args;
    private Command command;

    public CommandLineParser(String[] args) {
        this.args = args;
    }

    public void run() {
        String module = args[0];

        if ("conta".equals(module)) {
            if ("add".equals(args[1])) {
                ContaAddCommand command = new ContaAddCommand();

                command.setNome(args[2]);

                if (args.length > 2) {
                    if ("contabilizavel".equals(args[3])) {
                        command.setContabilizavel(true);
                    }
                }

                this.command = command;
                return;
            }
        }
    }

    public Command getCommand() {
        return command;
    }

    public List<String> getErrors() {
        // TODO Auto-generated method stub
        return null;
    }

}
