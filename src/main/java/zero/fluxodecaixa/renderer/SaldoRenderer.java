package zero.fluxodecaixa.renderer;

import java.util.List;

import zero.easymvc.Bean;
import zero.easymvc.Renderer;
import zero.fluxodecaixa.model.Saldo;

public class SaldoRenderer {

    @Bean
    private List<Saldo> saldos;

    @Renderer(path = { "saldo" })
    public void render() {
    }
}
