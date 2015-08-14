package zero.listprinter;

public class StringFormatter implements Formatter {

    private static StringFormatter instance;

    public static Formatter getInstance() {
        if (instance == null)
            instance = new StringFormatter();

        return instance;
    }

    @Override
    public String format(Object data) {
        return data.toString();
    }

}
