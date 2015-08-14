package zero.fluxodecaixa.app;

import zero.fluxodecaixa.model.Conta;
import zero.listprinter.Formatter;

public class ContaFormatter implements Formatter {

    private static ContaFormatter instance;

    public static ContaFormatter getInstance() {
        if (instance == null)
            instance = new ContaFormatter();

        return instance;
    }

    @Override
    public String format(Object data) {
        Conta conta = (Conta) data;

        return conta.getNome();
    }

}
