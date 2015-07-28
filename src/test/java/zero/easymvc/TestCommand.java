package zero.easymvc;

public class TestCommand {

    @CommandHandler(path = { "command", "subcommand" })
    public void execute(TestBean bean) {
        TestBean.commandRan = true;
    }
}
