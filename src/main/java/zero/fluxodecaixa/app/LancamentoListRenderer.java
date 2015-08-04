package zero.fluxodecaixa.app;

import java.util.LinkedList;
import java.util.List;

import zero.easymvc.Renderer;
import zero.fluxodecaixa.model.Lancamento;
import zero.fluxodecaixa.renderer.ColumnDefinition;
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

    private List<ColumnDefinition> createColumnDefinitions() {
        List<ColumnDefinition> defs = new LinkedList<ColumnDefinition>();

        defs.add(new ColumnDefinition("ID", "id", IDFormatter.getInstance()));
        defs.add(new ColumnDefinition("Data", "transacao", TransacaoFormatter.getInstance()));
        defs.add(new ColumnDefinition("Origem", "origem", ContaFormatter.getInstance()));
        defs.add(new ColumnDefinition("Destino", "destino", ContaFormatter.getInstance()));
        defs.add(new ColumnDefinition("Valor", "valor", MoneyFormatter.getInstance()));
        defs.add(new ColumnDefinition("Observacao", "observacao", StringFormatter.getInstance()));

        return defs;
    }
}
