package zero.fluxodecaixa;

import java.util.List;

import org.junit.Test;

import zero.easymvc.EasyMVCAssert;
import zero.easymvc.EasyMVCException;
import zero.fluxodecaixa.model.Saldo;

public class SaldoTests extends FluxodecaixaTest {

    public SaldoTests() {
        super("dbunit/saldotests_dataset.xml");
    }

    @Test
    public void should_calculate_saldos() throws EasyMVCException {
        // saldo [ --contas=<nomes das contas separados por ,> ] [ --diario |
        // --semanal | --mensal | --atual ]

        List<Object> beans = controller.run("saldo");

        EasyMVCAssert.assertBeanList(beans, 1);

        @SuppressWarnings("unchecked")
        List<Saldo> saldos = EasyMVCAssert.assertAndGetBean(beans, 0, List.class);

        Assert.assertSaldo("03/Ago/2015", "itau", "945", saldos.get(0));
        Assert.assertSaldo("03/Ago/2015", "bradesco", "925", saldos.get(1));

        Assert.assertSaldo("04/Ago/2015", "itau", "835", saldos.get(2));
        Assert.assertSaldo("04/Ago/2015", "bradesco", "833", saldos.get(3));
    }

}
