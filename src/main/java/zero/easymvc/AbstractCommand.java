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
    public String toString() {
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
