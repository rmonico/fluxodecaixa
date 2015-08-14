package zero.fluxodecaixa.app;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import zero.fluxodecaixa.model.Transacao;
import zero.listprinter.Formatter;

public class TransacaoFormatter implements Formatter {

    private static TransacaoFormatter instance;

    public static TransacaoFormatter getInstance() {
        if (instance == null)
            instance = new TransacaoFormatter();

        return instance;
    }

    DateFormat formatter = new SimpleDateFormat("dd/MMM");

    @Override
    public StringBuilder format(Object data) {
        Transacao transacao = (Transacao) data;

        Date date = transacao.getData().getTime();

        return new StringBuilder(formatter.format(date));
    }

}
