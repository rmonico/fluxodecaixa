package zero.fluxodecaixa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Calendar;

import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.model.Lancamento;
import zero.fluxodecaixa.model.Transacao;

class Assert {

    public static void assertConta(String nome, boolean contabilizavel, Conta conta) {
        assertEquals(nome, conta.getNome());
        assertEquals(contabilizavel, conta.isContabilizavel());
    }

    public static void assertTransacao(Calendar date, String observacao, Transacao transacao) {
        assertNotNull(transacao);
        assertEquals(date, transacao.getData());
        assertEquals(observacao, transacao.getObservacao());
    }

    public static void assertLancamento(String nomeOrigem, String nomeDestino, double valor, Lancamento lancamento) {
        assertContaByNome(nomeOrigem, lancamento.getOrigem());
        assertContaByNome(nomeDestino, lancamento.getDestino());
    }

    private static void assertContaByNome(String nomeConta, Conta conta) {
        if (nomeConta == null)
            assertNull(conta);
        else
            assertEquals(nomeConta, conta.getNome());
    }

}