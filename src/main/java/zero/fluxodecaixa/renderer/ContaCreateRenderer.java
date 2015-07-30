package zero.fluxodecaixa.renderer;

import zero.easymvc.Renderer;
import zero.fluxodecaixa.model.Conta;

public class ContaCreateRenderer {

    private Conta conta;

    @Renderer(path = { "conta", "add" })
    public void render() {
        System.out.println(String.format("Conta \"%s\" criada com sucesso.", conta.getNome()));
    }
}
