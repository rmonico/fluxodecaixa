package zero.fluxodecaixa;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import zero.easymvc.EasyMVCAssert;
import zero.easymvc.EasyMVCException;
import zero.fluxodecaixa.model.Conta;

public class ContaListTest extends FluxodecaixaTest {

    public ContaListTest() {
        super("dbunit/contatests_dataset.xml");
    }

    @Test
    public void contaLsCommand_should_return_all_available_contas() throws SQLException, EasyMVCException {
        List<Object> beans = controller.run("conta", "ls");

        EasyMVCAssert.assertBeanList(beans, 1);
        @SuppressWarnings("unchecked")
        List<Conta> contas = EasyMVCAssert.assertAndGetBean(beans, 0, List.class);

        assertEquals(3, contas.size());

        Assert.assertConta("carteira", true, contas.get(0));
        Assert.assertConta("casa", false, contas.get(1));
        Assert.assertConta("itau", true, contas.get(2));
    }

}
