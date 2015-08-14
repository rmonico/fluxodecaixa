package zero.listprinter;

import java.text.NumberFormat;

public class DoubleFormatter implements Formatter {

    private static DoubleFormatter instance;

    public static DoubleFormatter getInstance() {
        if (instance == null)
            instance = new DoubleFormatter();

        return instance;
    }

    @Override
    public String format(Object data) {
        double value = (double) data;

        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        return formatter.format(value);
    }

}
