package zero.fluxodecaixa.app;

import java.util.LinkedList;
import java.util.List;

import zero.easymvc.Renderer;
import zero.fluxodecaixa.model.Lancamento;
import zero.fluxodecaixa.renderer.Column;
import zero.fluxodecaixa.renderer.IDFormatter;
import zero.fluxodecaixa.renderer.ListPrinter;
import zero.fluxodecaixa.renderer.StringFormatter;

public class LancamentoListRenderer {

    private List<Lancamento> lancamentos;

    @Renderer(path = { "lanc", "ls" })
    public void render() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        ListPrinter printer = new ListPrinter();

        printer.setEntityName("Lançamento", "Lançamentos");

        printer.setColumnDefinitions(createColumnDefinitions());

        printer.setData(lancamentos);

        printer.print();
    }

    private List<Column> createColumnDefinitions() {
        List<Column> defs = new LinkedList<Column>();

        defs.add(new Column("ID", "id", IDFormatter.getInstance()));
        defs.add(new Column("Data", "transacao", TransacaoFormatter.getInstance()));
        defs.add(new Column("Origem", "origem", ContaFormatter.getInstance()));
        defs.add(new Column("Destino", "destino", ContaFormatter.getInstance()));
        defs.add(new Column("Valor", "valor", MoneyFormatter.getInstance()));
        defs.add(new Column("Observacao", "observacao", StringFormatter.getInstance()));

        return defs;
    }
}
