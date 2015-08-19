package zero.fluxodecaixa.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import zero.easymvc.Bean;
import zero.easymvc.CommandHandler;
import zero.easymvc.Dependency;
import zero.fluxodecaixa.app.dao.LancamentoDao;
import zero.fluxodecaixa.app.dao.TransacaoDao;
import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.model.Lancamento;
import zero.fluxodecaixa.model.Saldo;

import com.j256.ormlite.stmt.PreparedQuery;

public class SaldoCommand {

    @Dependency
    private LancamentoDao dao;

    @Dependency
    private TransacaoDao transacaoDao;

    @Bean
    private List<Saldo> saldos;

    private Saldo currentSaldo;

    private Calendar currentDay;

    @CommandHandler(path = { "saldo" })
    public void execute() throws SQLException, ParseException {
        PreparedQuery<Lancamento> query = dao.createQuerySortedByTransacaoData(transacaoDao);

        List<Lancamento> lancamentos = dao.query(query);

        saldos = new LinkedList<>();

        if (lancamentos.isEmpty())
            return;

        currentDay = lancamentos.get(0).getTransacao().getData();

        currentSaldo = new Saldo(currentDay);

        for (Lancamento lancamento : lancamentos) {
            if (dayChanged(lancamento)) {
                saldos.add(currentSaldo);

                currentDay = lancamento.getTransacao().getData();

                currentSaldo = new Saldo(currentDay, currentSaldo);
            }

            makeAccount(lancamento, true);

            makeAccount(lancamento, false);
        }

        saldos.add(currentSaldo);
    }

    private boolean dayChanged(Lancamento lancamento) {
        Calendar lancamentoDay = lancamento.getTransacao().getData();

        return !lancamentoDay.equals(currentDay);
    }

    private void makeAccount(Lancamento lancamento, boolean origem) {
        Conta conta = origem ? lancamento.getOrigem() : lancamento.getDestino();
        Conta anotherConta = origem ? lancamento.getDestino() : lancamento.getOrigem();

        if (!conta.isContabilizavel())
            return;

        BigDecimal newSaldoValor;

        if (anotherConta.isSaldo()) {
            newSaldoValor = new BigDecimal(lancamento.getValor());
        } else {
            String nomeConta = conta.getNome();
            newSaldoValor = getNewSaldoValueForNonSaldoConta(lancamento, nomeConta, origem);
        }

        currentSaldo.getValores().put(conta.getNome(), newSaldoValor);
    }

    private BigDecimal getNewSaldoValueForNonSaldoConta(Lancamento lancamento, String nomeConta, boolean origem) {
        BigDecimal oldSaldoValor = currentSaldo.getValores().get(nomeConta);

        if (oldSaldoValor == null)
            oldSaldoValor = BigDecimal.ZERO;

        BigDecimal lancamentoValor = new BigDecimal(lancamento.getValor());

        return origem ? oldSaldoValor.subtract(lancamentoValor) : oldSaldoValor.add(lancamentoValor);
    }

}
