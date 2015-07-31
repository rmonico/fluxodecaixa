package zero.fluxodecaixa;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import zero.easymvc.EasyMVCAssert;
import zero.easymvc.EasyMVCException;
import zero.fluxodecaixa.app.LancamentoCreateCommand;
import zero.fluxodecaixa.model.Lancamento;
import zero.fluxodecaixa.renderer.LancamentoCreateRenderer;

public class LancamentoCreateTest extends FluxodecaixaTest {

    public LancamentoCreateTest() {
        super("dbunit/contals_dataset.xml");
    }

    @Before
    public void before() {
        controller.registerCommandHandler(LancamentoCreateCommand.class);

        controller.registerRenderer(LancamentoCreateRenderer.class);
    }

    @Test
    public void should_create_a_lancamento_with_minimal_command_line() throws EasyMVCException {
        List<Object> beans = controller.run("lanc", "add", "itau", "carteira", "1.99");

        EasyMVCAssert.assertBeanList(beans, 1);

        Lancamento lancamento = EasyMVCAssert.assertAndGetBean(beans, 0, Lancamento.class);

        Calendar today = GregorianCalendar.getInstance();

        Assert.assertLancamento(today, "itau", "carteira", 1.99d, null, lancamento);
    }

    @Test
    public void should_create_a_lancamento_with_observacao() throws EasyMVCException {
        List<Object> beans = controller.run("lanc", "add", "itau", "carteira", "1.99", "Note field on lancamento");

        EasyMVCAssert.assertBeanList(beans, 1);

        Lancamento lancamento = EasyMVCAssert.assertAndGetBean(beans, 0, Lancamento.class);

        Calendar today = GregorianCalendar.getInstance();

        Assert.assertLancamento(today, "itau", "carteira", 1.99d, "Note field on lancamento", lancamento);
    }
}
