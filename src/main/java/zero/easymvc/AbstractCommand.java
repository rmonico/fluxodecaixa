package zero.easymvc;

import java.util.Arrays;

public class AbstractCommand implements Command {

    private Object[] args;

    public AbstractCommand(Object[] args) {
        this.args = args;
    }

    @Override
    public Object[] args() {
        return args;
    }

    @Override
    public boolean isSameCommand(Command command) {
        if (args.length > command.args().length) {
            return false;
        }

        for (int i = 0; i < args.length; i++) {
            Object myArg = args[i];
            Object otherArg = command.args()[i];

            if (!myArg.equals(otherArg)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        if (args.length == 0)
            return "";

        StringBuilder sb = new StringBuilder("[");

        for (Object o : args) {
            sb.append("\"");
            sb.append(o.toString());
            sb.append("\", ");
        }

        sb.replace(sb.length() - 2, sb.length(), "]");

        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(args);

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        AbstractCommand other = (AbstractCommand) obj;

        if (!Arrays.equals(args, other.args))
            return false;

        return true;
    }

}
