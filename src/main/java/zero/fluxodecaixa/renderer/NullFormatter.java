package zero.fluxodecaixa.renderer;

public class NullFormatter implements Formatter {

    private static Formatter instance;

    public static Formatter getInstance() {
        if (instance == null)
            instance = new NullFormatter();

        return instance;
    }

    @Override
    public StringBuilder format(Object data) {
        return new StringBuilder("<null>");
    }

}
