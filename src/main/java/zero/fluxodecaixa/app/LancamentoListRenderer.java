package zero.fluxodecaixa.app;

import java.util.LinkedList;
import java.util.List;

import zero.easymvc.Renderer;
import zero.fluxodecaixa.model.Lancamento;
import zero.fluxodecaixa.renderer.Column;
import zero.fluxodecaixa.renderer.IDFormatter;
import zero.fluxodecaixa.renderer.ListPrinter;
import zero.fluxodecaixa.renderer.ListPrinterException;
import zero.fluxodecaixa.renderer.ReflectionFieldColumn;
import zero.fluxodecaixa.renderer.StringFormatter;

public class LancamentoListRenderer {

    private List<Lancamento> lancamentos;

    @Renderer(path = { "lanc", "ls" })
    public void render() throws ListPrinterException {
        ListPrinter printer = new ListPrinter();

        printer.setEntityName("Lançamento", "Lançamentos");

        printer.setColumnDefinitions(createColumnDefinitions());

        printer.setData(lancamentos);

        printer.print();
    }

    private List<Column> createColumnDefinitions() {
        List<Column> defs = new LinkedList<Column>();

        defs.add(new ReflectionFieldColumn("ID", "id", IDFormatter.getInstance()));
        defs.add(new ReflectionFieldColumn("Data", "transacao", TransacaoFormatter.getInstance()));
        defs.add(new ReflectionFieldColumn("Origem", "origem", ContaFormatter.getInstance()));
        defs.add(new ReflectionFieldColumn("Destino", "destino", ContaFormatter.getInstance()));
        defs.add(new ReflectionFieldColumn("Valor", "valor", MoneyFormatter.getInstance()));
        defs.add(new ReflectionFieldColumn("Observacao", "observacao", StringFormatter.getInstance()));

        return defs;
    }
}
