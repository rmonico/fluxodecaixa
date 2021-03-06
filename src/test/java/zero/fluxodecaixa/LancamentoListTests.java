package zero.fluxodecaixa;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import zero.easymvc.EasyMVCAssert;
import zero.easymvc.EasyMVCException;
import zero.fluxodecaixa.model.Lancamento;

public class LancamentoListTests extends FluxodecaixaTest {

    public LancamentoListTests() {
        super("dbunit/lancamentotests_dataset.xml");
    }

    @Test
    public void should_return_all_lancamentos_sorted_by_date() throws EasyMVCException {
        List<Object> beans = controller.run("lanc", "ls");

        EasyMVCAssert.assertBeanList(beans, 1);

        @SuppressWarnings("unchecked")
        List<Lancamento> lancamentos = EasyMVCAssert.assertAndGetBean(beans, 0, List.class);

        assertEquals(3, lancamentos.size());

        Assert.assertLancamento("15/Jun/2015", "carteira", "casa", 10d, "Pão", lancamentos.get(0));
        Assert.assertLancamento("16/Jun/2015", "itau", "carteira", 50d, "Dinheiro sacado", lancamentos.get(1));
        Assert.assertLancamento("16/Jun/2015", "carteira", "casa", 25.5d, "Dinheiro da feira", lancamentos.get(2));
    }
}
