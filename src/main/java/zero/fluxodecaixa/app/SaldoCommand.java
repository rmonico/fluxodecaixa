package zero.fluxodecaixa.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

    private Map<Conta, Saldo> currentSaldos;

    private Calendar currentDay;

    @CommandHandler(path = { "saldo" })
    public void execute() throws SQLException, ParseException {
        PreparedQuery<Lancamento> query = dao.createQuerySortedByTransacaoData(transacaoDao);

        List<Lancamento> lancamentos = dao.query(query);

        saldos = new LinkedList<>();

        if (lancamentos.isEmpty())
            return;

        currentSaldos = new HashMap<>();

        currentDay = lancamentos.get(0).getTransacao().getData();

        for (Lancamento lancamento : lancamentos) {
            if (dayChanged(lancamento))
                postSaldosOfTheDay();

            makeAccount(lancamento, true);

            makeAccount(lancamento, false);

            currentDay = lancamento.getTransacao().getData();
        }

        postSaldosOfTheDay();
    }

    private void postSaldosOfTheDay() {
        for (Entry<Conta, Saldo> entry : currentSaldos.entrySet()) {
            Saldo saldo = entry.getValue();

            Saldo newSaldo = new Saldo();
            newSaldo.setData(currentDay);
            newSaldo.setConta(saldo.getConta());
            newSaldo.setValor(saldo.getValor());

            saldos.add(newSaldo);
        }
    }

    private void makeAccount(Lancamento lancamento, boolean origem) {
        Conta conta = origem ? lancamento.getOrigem() : lancamento.getDestino();

        if (!conta.isContabilizavel())
            return;

        Saldo saldo = getOrCreateSaldoForConta(conta);

        BigDecimal oldSaldoValor = saldo.getValor();
        BigDecimal lancamentoValor = new BigDecimal(lancamento.getValor());

        BigDecimal newSaldoValor;

        if (origem)
            newSaldoValor = oldSaldoValor.subtract(lancamentoValor);
        else
            newSaldoValor = oldSaldoValor.add(lancamentoValor);

        saldo.setValor(newSaldoValor);
    }

    private Saldo getOrCreateSaldoForConta(Conta conta) {
        Saldo saldo = currentSaldos.get(conta);

        if (saldo == null) {
            saldo = new Saldo();
            saldo.setConta(conta);
            saldo.setValor(BigDecimal.ZERO);

            currentSaldos.put(conta, saldo);
        }

        return saldo;
    }

    private boolean dayChanged(Lancamento lancamento) {
        Calendar lancamentoDay = lancamento.getTransacao().getData();

        return !lancamentoDay.equals(currentDay);
    }
}
