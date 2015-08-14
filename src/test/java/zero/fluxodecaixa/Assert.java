package zero.fluxodecaixa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Map;
import java.util.Map.Entry;

import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.model.Lancamento;
import zero.fluxodecaixa.model.Saldo;
import zero.fluxodecaixa.model.Transacao;
import zero.utils.TimeUtils;

class Assert {

    public static void assertConta(String nome, boolean contabilizavel, Conta conta) {
        assertNotNull(conta);
        assertEquals(nome, conta.getNome());
        assertEquals(contabilizavel, conta.isContabilizavel());
    }

    public static void assertTransacao(String date, String observacao, Transacao transacao) {
        assertNotNull(transacao);

        assertData(date, transacao.getData());

        assertEquals(observacao, transacao.getObservacao());
    }

    public static void assertLancamento(String date, String nomeOrigem, String nomeDestino, double valor, String observacao, Lancamento lancamento) {
        if (date == null)
            assertNull(lancamento.getTransacao());
        else {
            assertNotNull(lancamento.getTransacao());
            Assert.assertTransacao(date, null, lancamento.getTransacao());
        }

        assertContaByNome("Conta origem", nomeOrigem, lancamento.getOrigem());
        assertContaByNome("Conta destino", nomeDestino, lancamento.getDestino());

        assertEquals(valor, lancamento.getValor(), 0d);

        if (observacao == null)
            assertNull(lancamento.getObservacao());
        else {
            assertNotNull(lancamento.getObservacao());
            assertEquals(observacao, lancamento.getObservacao());
        }
    }

    private static void assertContaByNome(String mensagem, String nomeConta, Conta conta) {
        assertNotNull(mensagem, conta);
        assertEquals(mensagem, nomeConta, conta.getNome());
    }

    public static void assertSaldo(String expectedData, Map<String, String> expectedValores, Saldo actual) {
        assertNotNull(actual);

        assertData(expectedData, actual.getData());

        assertEquals("Keyset", expectedValores.keySet().size(), actual.getValores().keySet().size());

        for (Entry<Conta, BigDecimal> entry : actual.getValores().entrySet()) {
            Conta actualConta = entry.getKey();

            String expectedSaldoValor = expectedValores.get(actualConta.getNome());
            assertNotNull("nome conta", expectedSaldoValor);

            assertEquals("saldo valor", new BigDecimal(expectedSaldoValor), entry.getValue());
        }
    }

    public static void assertData(String expectedData, Calendar actualData) {
        if (expectedData == null) {
            assertNull("data should be null", actualData);
        } else {
            assertNotNull("data cant be null", actualData);
            assertEquals("data", expectedData, TimeUtils.dateToString(actualData));
        }
    }

}
