package zero.fluxodecaixa;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        assertEquals(2, saldos.size());

        Map<String, String> expectedValores = new HashMap<String, String>();
        expectedValores.put("bradesco", "925");
        expectedValores.put("itau", "945");

        Assert.assertSaldo("03/Ago/2015", expectedValores, saldos.get(0));

        expectedValores = new HashMap<String, String>();
        expectedValores.put("bradesco", "833");
        expectedValores.put("itau", "835");

        Assert.assertSaldo("04/Ago/2015", expectedValores, saldos.get(1));
    }

}
