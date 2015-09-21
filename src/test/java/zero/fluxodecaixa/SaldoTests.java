package zero.fluxodecaixa;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import zero.easymvc.EasyMVCAssert;
import zero.easymvc.EasyMVCException;
import zero.fluxodecaixa.model.Saldo;
import zero.utils.TimeUtils;

public class SaldoTests extends FluxodecaixaTest {

    public SaldoTests() {
        super("dbunit/saldotests_dataset.xml");
    }

    @Test
    public void should_calculate_saldos() throws EasyMVCException {
        List<Object> beans = controller.run("saldo");

        EasyMVCAssert.assertBeanList(beans, 1);

        @SuppressWarnings("unchecked")
        List<Saldo> saldos = EasyMVCAssert.assertAndGetBean(beans, 0, List.class);

        assertEquals(2, saldos.size());

        Map<String, String> expectedValores = new HashMap<String, String>();
        expectedValores.put("bradesco", "925");
        expectedValores.put("itau", "945");

        Assert.assertSaldo("03/Aug/2015", expectedValores, saldos.get(0));

        expectedValores = new HashMap<String, String>();
        expectedValores.put("bradesco", "833");
        expectedValores.put("itau", "835");

        Assert.assertSaldo("04/Aug/2015", expectedValores, saldos.get(1));
    }

    @Test
    public void should_put_saldos_of_today_together() throws EasyMVCException {
        controller.run("lanc", "add", "itau", "casa", "100");
        controller.run("lanc", "add", "salário", "itau", "50");

        controller.run("lanc", "add", "bradesco", "casa", "80");
        controller.run("lanc", "add", "salário", "bradesco", "130");

        List<Object> beans = controller.run("saldo");

        EasyMVCAssert.assertBeanList(beans, 1);

        @SuppressWarnings("unchecked")
        List<Saldo> saldos = EasyMVCAssert.assertAndGetBean(beans, 0, List.class);

        assertEquals(3, saldos.size());

        Map<String, String> expectedValores = new HashMap<String, String>();
        expectedValores.put("bradesco", "883");
        expectedValores.put("itau", "785");

        Calendar today = GregorianCalendar.getInstance();

        Assert.assertSaldo(TimeUtils.dateToString(today), expectedValores, saldos.get(2));
    }

    @Test
    public void should_reset_conta_saldo_when_found_a_saldo_lancamento() throws EasyMVCException {
        controller.run("lanc", "add", "saldo", "itau", "1230");
        controller.run("lanc", "add", "saldo", "bradesco", "4500");

        List<Object> beans = controller.run("saldo");

        EasyMVCAssert.assertBeanList(beans, 1);

        @SuppressWarnings("unchecked")
        List<Saldo> saldos = EasyMVCAssert.assertAndGetBean(beans, 0, List.class);

        assertEquals(3, saldos.size());

        Map<String, String> expectedValores = new HashMap<String, String>();
        expectedValores.put("bradesco", "4500");
        expectedValores.put("itau", "1230");

        Calendar today = GregorianCalendar.getInstance();

        Assert.assertSaldo(TimeUtils.dateToString(today), expectedValores, saldos.get(2));
    }
}
