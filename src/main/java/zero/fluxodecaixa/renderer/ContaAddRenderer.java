package zero.fluxodecaixa.renderer;

import zero.easymvc.Renderer;
import zero.fluxodecaixa.app.ContaAddBean;

public class ContaAddRenderer {

    @Renderer
    public void render(ContaAddBean bean) {
        System.out.println(String.format("Conta \"%s\" criada com sucesso.", bean.getConta().getNome()));
    }
}
