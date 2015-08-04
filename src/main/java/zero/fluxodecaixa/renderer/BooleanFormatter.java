package zero.fluxodecaixa.renderer;

public class BooleanFormatter implements Formatter {

    private static BooleanFormatter instance;

    public static Formatter getInstance() {
        if (instance == null)
            instance = new BooleanFormatter();

        return instance;
    }

    @Override
    public StringBuilder format(Object data) {
        Boolean booleanData = (Boolean) data;

        return booleanData ? new StringBuilder("True") : new StringBuilder("False");
    }

}
