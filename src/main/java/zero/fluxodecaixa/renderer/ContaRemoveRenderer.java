package zero.fluxodecaixa.renderer;

import zero.easymvc.Renderer;
import zero.fluxodecaixa.model.Conta;

public class ContaRemoveRenderer {
    private Conta removedConta;

    @Renderer(path = { "conta", "rm" })
    public void render() {
        System.out.println(String.format("Conta \"%s\" removida.", removedConta.getNome()));
    }

}
