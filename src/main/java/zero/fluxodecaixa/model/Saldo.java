package zero.fluxodecaixa.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Saldo {
    private Calendar data;
    private Map<String, BigDecimal> valores;

    public Saldo(Calendar data) {
        this(data, new HashMap<String, BigDecimal>());
    }

    public Saldo(Calendar data, Saldo other) {
        this(data, other.valores);
    }

    private Saldo(Calendar data, Map<String, BigDecimal> valores) {
        // Dont need to clone map's values, they are immutable
        this.valores = new HashMap<String, BigDecimal>(valores);

        this.data = data;
    }

    public Calendar getData() {
        return data;
    }

    public Map<String, BigDecimal> getValores() {
        return valores;
    }

}
