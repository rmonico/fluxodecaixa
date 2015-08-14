package zero.fluxodecaixa.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Saldo {
    private Calendar data;
    private Map<Conta, BigDecimal> valores;

    public Saldo(Calendar data) {
        this(data, new HashMap<Conta, BigDecimal>());
    }

    public Saldo(Calendar data, Saldo other) {
        this(data, other.valores);
    }

    private Saldo(Calendar data, Map<Conta, BigDecimal> valores) {
        // Dont need to clone map's values, they are immutable
        this.valores = new HashMap<Conta, BigDecimal>(valores);

        this.data = data;
    }

    public Calendar getData() {
        return data;
    }

    public Map<Conta, BigDecimal> getValores() {
        return valores;
    }

}
