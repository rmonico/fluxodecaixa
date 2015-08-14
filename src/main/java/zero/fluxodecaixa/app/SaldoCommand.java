package zero.fluxodecaixa.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import zero.easymvc.Bean;
import zero.easymvc.CommandHandler;
import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.model.Saldo;
import zero.utils.TimeUtils;

public class SaldoCommand {

    @Bean
    private List<Saldo> saldos;

    @CommandHandler(path = { "saldo" })
    public void execute() throws SQLException, ParseException {
        saldos = new LinkedList<>();

//        Assert.assertSaldo("03/Ago/2015", "itau", "945", saldos.get(0));
        Saldo saldo = new Saldo();
        saldo.setData(TimeUtils.stringToDate("03/ago/2015"));
        Conta conta = new Conta();
        conta.setNome("itau");
        saldo.setConta(conta);
        saldo.setValor(new BigDecimal("945"));
        saldos.add(saldo);

//        Assert.assertSaldo("03/Ago/2015", "bradesco", "925", saldos.get(1));
        saldo = new Saldo();
        saldo.setData(TimeUtils.stringToDate("03/ago/2015"));
        conta = new Conta();
        conta.setNome("bradesco");
        saldo.setConta(conta);
        saldo.setValor(new BigDecimal("925"));
        saldos.add(saldo);

//        Assert.assertSaldo("04/Ago/2015", "itau", "835", saldos.get(2));
        saldo = new Saldo();
        saldo.setData(TimeUtils.stringToDate("04/ago/2015"));
        conta = new Conta();
        conta.setNome("itau");
        saldo.setConta(conta);
        saldo.setValor(new BigDecimal("835"));
        saldos.add(saldo);

//        Assert.assertSaldo("04/Ago/2015", "bradesco", "833", saldos.get(3));
        saldo = new Saldo();
        saldo.setData(TimeUtils.stringToDate("04/ago/2015"));
        conta = new Conta();
        conta.setNome("bradesco");
        saldo.setConta(conta);
        saldo.setValor(new BigDecimal("833"));
        saldos.add(saldo);
    }
}
