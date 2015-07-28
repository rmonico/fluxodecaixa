package zero.easymvc;

public interface Command {

    public Object[] args();

    public boolean isSameCommand(Command command);

}
