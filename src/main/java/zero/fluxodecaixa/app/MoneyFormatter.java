package zero.fluxodecaixa.app;

import java.text.NumberFormat;

import zero.fluxodecaixa.renderer.Formatter;

public class MoneyFormatter implements Formatter {

    private static MoneyFormatter instance;

    public static MoneyFormatter getInstance() {
        if (instance == null)
            instance = new MoneyFormatter();

        return instance;
    }

    @Override
    public StringBuilder format(Object data) {
        double value = (double) data;

        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        return new StringBuilder(formatter.format(value));
    }

}
