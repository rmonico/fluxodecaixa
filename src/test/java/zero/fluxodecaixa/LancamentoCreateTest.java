package zero.fluxodecaixa;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.dbunit.DatabaseUnitException;
import org.junit.Test;

import zero.easymvc.EasyMVCAssert;
import zero.easymvc.EasyMVCException;
import zero.fluxodecaixa.app.LancamentoCreateCommand;
import zero.fluxodecaixa.model.Lancamento;
import zero.fluxodecaixa.renderer.LancamentoCreateRenderer;
import zero.utils.TimeUtils;

public class LancamentoCreateTest extends FluxodecaixaTest {

    public LancamentoCreateTest() {
        super("dbunit/lancamentotests_dataset.xml");
    }

    @Override
    public void before() throws ClassNotFoundException, FileNotFoundException, SQLException, DatabaseUnitException {
        super.before();

        controller.registerCommandHandler(LancamentoCreateCommand.class);

        controller.registerRenderer(LancamentoCreateRenderer.class);
    }

    @Test
    public void should_create_a_lancamento_with_minimal_command_line() throws EasyMVCException {
        List<Object> beans = controller.run("lanc", "add", "itau", "carteira", "1.99");

        EasyMVCAssert.assertBeanList(beans, 1);

        Lancamento lancamento = EasyMVCAssert.assertAndGetBean(beans, 0, Lancamento.class);

        Calendar today = GregorianCalendar.getInstance();

        Assert.assertLancamento(TimeUtils.dateToString(today), "itau", "carteira", 1.99d, null, lancamento);
    }

    @Test
    public void should_create_a_lancamento_with_observacao() throws EasyMVCException {
        List<Object> beans = controller.run("lanc", "add", "itau", "carteira", "1.99", "Note field on lancamento");

        EasyMVCAssert.assertBeanList(beans, 1);

        Lancamento lancamento = EasyMVCAssert.assertAndGetBean(beans, 0, Lancamento.class);

        Calendar today = GregorianCalendar.getInstance();

        Assert.assertLancamento(TimeUtils.dateToString(today), "itau", "carteira", 1.99d, "Note field on lancamento", lancamento);
    }

    @Test
    public void should_create_a_lancamento_for_a_existing_transaction() throws EasyMVCException {
        List<Object> beans = controller.run("lanc", "add", "carteira", "casa", "500", "--", "--transacao-id=1");

        EasyMVCAssert.assertBeanList(beans, 1);

        Lancamento lancamento = EasyMVCAssert.assertAndGetBean(beans, 0, Lancamento.class);

        Assert.assertLancamento("15/Jun/2015", "carteira", "casa", 500d, null, lancamento);
    }

    @Test
    public void should_create_a_lancamento_to_a_specific_date() throws EasyMVCException {
        List<Object> beans = controller.run("lanc", "add", "carteira", "casa", "15", "--", "--data=30/Abr/2015");

        EasyMVCAssert.assertBeanList(beans, 1);

        Lancamento lancamento = EasyMVCAssert.assertAndGetBean(beans, 0, Lancamento.class);

        Assert.assertLancamento("30/Abr/2015", "carteira", "casa", 15d, null, lancamento);
    }

    @Test(expected = EasyMVCException.class)
    public void should_throw_exception_when_pass_data_and_transaction_id() throws EasyMVCException {
        controller.run("lanc", "add", "carteira", "casa", "1.99", "--", "--data=30/Abr/2015", "--transacao-id=4");
    }
}
