package zero.easymvc;

public class TestCommand {

    @Command(path = { "command", "subcommand" })
    public void execute(TestBean bean) {

    }
}
