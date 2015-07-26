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

        if ("conta".equals(args[0])) {
            parseContaModule();
        } else {
            errors.add("Invalid module: \"invalid module name\"");
        }
    }

    private void parseContaModule() {
        if (args.length == 1) {
            errors.add("\"conta\" no command");
            return;
        }

        String commandName = args[1];

        if ("add".equals(commandName)) {
            parseContaAdd();
        } else if ("ls".equals(commandName)) {
            parseContaLs();
        }
    }

    private void parseContaAdd() {
        if (args.length == 2) {
            errors.add("\"conta add\" no argument");
            return;
        }

        ContaAddCommand command = new ContaAddCommand();

        command.setNome(args[2]);

        if (args.length == 4) {
            if ("contabilizavel".equals(args[3])) {
                command.setContabilizavel(true);
            }
        } else {
            errors.add("\"conta add\" extra argument(s)");
            return;
        }

        this.command = command;
    }

    private void parseContaLs() {
        if (args.length != 2) {
            errors.add("\"conta ls\" extra argument(s)");
            return;
        }

        command = new ContaLsCommand();
    }

    public Command getCommand() {
        return command;
    }

    public List<String> getErrors() {
        return errors;
    }

}
