package zero.fluxodecaixa;

import static org.junit.Assert.assertEquals;
import zero.fluxodecaixa.model.Conta;

public class ContaTest extends DatabaseTest {

    protected void assertConta(String nome, boolean contabilizavel, Conta conta) {
        assertEquals(nome, conta.getNome());
        assertEquals(contabilizavel, conta.isContabilizavel());
    }
}
