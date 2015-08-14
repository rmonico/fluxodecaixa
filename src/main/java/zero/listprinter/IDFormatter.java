package zero.listprinter;

public class IDFormatter implements Formatter {

    private static IDFormatter instance;

    public static Formatter getInstance() {
        if (instance == null)
            instance = new IDFormatter();

        return instance;
    }

    @Override
    public String format(Object data) {
        StringBuilder formatted = new StringBuilder();

        formatted.append("#");
        formatted.append(data.toString());

        return formatted.toString();
    }

}
