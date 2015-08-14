package zero.listprinter;

public class BooleanFormatter implements Formatter {

    private static BooleanFormatter instance;

    public static Formatter getInstance() {
        if (instance == null)
            instance = new BooleanFormatter();

        return instance;
    }

    @Override
    public String format(Object data) {
        Boolean booleanData = (Boolean) data;

        return booleanData ? "True" : "False";
    }

}
