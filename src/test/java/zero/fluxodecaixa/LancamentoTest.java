package zero.fluxodecaixa;

import java.util.Calendar;

import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.model.Lancamento;
import zero.fluxodecaixa.model.Transacao;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

public class LancamentoTest extends FluxodecaixaTest {

    protected void assertTransacao(Calendar date, String observacao, Transacao transacao) {
        assertNotNull(transacao);
        assertEquals(date, transacao.getData());
        assertEquals(observacao, transacao.getObservacao());
    }

    protected void assertLancamento(String nomeOrigem, String nomeDestino, double valor, Lancamento lancamento) {
        assertContaByNome(nomeOrigem, lancamento.getOrigem());
        assertContaByNome(nomeDestino, lancamento.getDestino());
    }

    private void assertContaByNome(String nomeConta, Conta conta) {
        if (nomeConta == null)
            assertNull(conta);
        else
            assertEquals(nomeConta, conta.getNome());
    }

}
