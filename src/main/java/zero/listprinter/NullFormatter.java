package zero.listprinter;

public class NullFormatter implements Formatter {

    private static Formatter instance;

    public static Formatter getInstance() {
        if (instance == null)
            instance = new NullFormatter();

        return instance;
    }

    @Override
    public String format(Object data) {
        return "<null>";
    }

}
