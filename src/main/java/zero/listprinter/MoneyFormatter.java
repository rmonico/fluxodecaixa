package zero.listprinter;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class MoneyFormatter implements Formatter {

    private static MoneyFormatter instance;

    public static MoneyFormatter getInstance() {
        if (instance == null)
            instance = new MoneyFormatter();

        return instance;
    }

    @Override
    public String format(Object data) {
        BigDecimal value = (BigDecimal) data;

        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        return formatter.format(value);
    }
}
