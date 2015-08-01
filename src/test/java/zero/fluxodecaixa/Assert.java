package zero.fluxodecaixa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.model.Lancamento;
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
        if (date == null)
            assertNull(transacao.getData());
        else {
            assertNotNull(transacao.getData());
            assertEquals(date, TimeUtils.dateToString(transacao.getData()));
        }

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

}
