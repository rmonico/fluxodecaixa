package zero.fluxodecaixa;

import static org.junit.Assert.assertEquals;
import zero.fluxodecaixa.model.Conta;

class Assert {

    public static void assertConta(String nome, boolean contabilizavel, Conta conta) {
        assertEquals(nome, conta.getNome());
        assertEquals(contabilizavel, conta.isContabilizavel());
    }
}
