package zero.fluxodecaixa.renderer;

import java.util.LinkedList;
import java.util.List;

import zero.easymvc.Renderer;
import zero.fluxodecaixa.model.Conta;
import zero.listprinter.BooleanFormatter;
import zero.listprinter.Column;
import zero.listprinter.IDFormatter;
import zero.listprinter.ListPrinter;
import zero.listprinter.ListPrinterException;
import zero.listprinter.FormattedColumn;
import zero.listprinter.ReflectionFieldExtractor;
import zero.listprinter.StringFormatter;

public class ContaListRenderer {

    private List<Conta> contas;

    @Renderer(path = { "conta", "ls" })
    public void render() throws ListPrinterException {
        ListPrinter printer = new ListPrinter();

        printer.setEntityName("Conta", "Contas");

        printer.setColumnDefinitions(createColumnDefinitions());

        printer.setData(contas);

        printer.print();
    }

    private List<Column> createColumnDefinitions() {
        List<Column> defs = new LinkedList<>();

        defs.add(new FormattedColumn("ID", new ReflectionFieldExtractor("id"), IDFormatter.getInstance()));
        defs.add(new FormattedColumn("Nome", new ReflectionFieldExtractor("nome"), StringFormatter.getInstance()));
        defs.add(new FormattedColumn("Contabilizável", new ReflectionFieldExtractor("contabilizavel"), BooleanFormatter.getInstance()));

        return defs;
    }
}
