package zero.fluxodecaixa.app;

import java.util.LinkedList;
import java.util.List;

import zero.easymvc.Renderer;
import zero.fluxodecaixa.model.Lancamento;
import zero.listprinter.Column;
import zero.listprinter.DateFormatter;
import zero.listprinter.IDFormatter;
import zero.listprinter.ListPrinter;
import zero.listprinter.ListPrinterException;
import zero.listprinter.DoubleFormatter;
import zero.listprinter.FormattedColumn;
import zero.listprinter.ReflectionFieldExtractor;
import zero.listprinter.StringFormatter;

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

        defs.add(new FormattedColumn("ID", new ReflectionFieldExtractor("id"), IDFormatter.getInstance()));
        defs.add(new FormattedColumn("Data", new ReflectionFieldExtractor("transacao", new ReflectionFieldExtractor("data")), DateFormatter.getInstance()));
        defs.add(new FormattedColumn("Origem", new ReflectionFieldExtractor("origem", new ReflectionFieldExtractor("nome")), StringFormatter.getInstance()));
        defs.add(new FormattedColumn("Destino", new ReflectionFieldExtractor("destino", new ReflectionFieldExtractor("nome")), StringFormatter.getInstance()));
        defs.add(new FormattedColumn("Valor", new ReflectionFieldExtractor("valor"), DoubleFormatter.getInstance()));
        defs.add(new FormattedColumn("Observacao", new ReflectionFieldExtractor("observacao"), StringFormatter.getInstance()));

        return defs;
    }
}
