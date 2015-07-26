package zero.fluxodecaixa;

import java.util.ArrayList;
import java.util.List;

public class CommandLineParser {

    private String[] args;
    private Command command;
    private List<String> errors;

    public CommandLineParser(String[] args) {
        this.args = args;
        errors = new ArrayList<String>();
    }

    public void run() {
        if (args.length == 0) {
            errors.add("Empty command line");
            return;
        }

        String module = args[0];

        if ("conta".equals(module)) {
            if (args.length == 1) {
                errors.add("\"conta\" no command");
                return;
            }

            if ("add".equals(args[1])) {
                if (args.length == 2) {
                    errors.add("\"conta add\" invalid params");
                    return;
                }

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
        } else {
            errors.add("Invalid module: \"invalid module name\"");
        }
    }

    public Command getCommand() {
        return command;
    }

    public List<String> getErrors() {
        return errors;
    }

}
