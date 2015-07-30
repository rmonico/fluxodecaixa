package zero.fluxodecaixa.app;

import java.util.Calendar;

import zero.easymvc.FlagParameter;
import zero.easymvc.PositionalParameter;

public class LancamentoCreateArguments {
    @PositionalParameter
    private String origemNome;

    @PositionalParameter(after = "origemNome")
    private String destinoNome;

    @PositionalParameter(after = "valor")
    private Double valor;

    @FlagParameter(token = { "-d", "--data" })
    private Calendar data;

    @FlagParameter(token = { "-o", "--observacao" })
    private String observacao;
}
