package zero.fluxodecaixa.renderer;

import java.math.BigDecimal;
import java.util.Map;

import zero.listprinter.DataExtractor;
import zero.listprinter.ListPrinterException;

public class TotalSaldoCalculator implements DataExtractor {

    @Override
    public Object extract(Object data) throws ListPrinterException {
        @SuppressWarnings("unchecked")
        Map<String, BigDecimal> map = (Map<String, BigDecimal>) data;

        BigDecimal total = BigDecimal.ZERO;

        for (BigDecimal saldo : map.values())
            total = total.add(saldo);

        return total;
    }

}
