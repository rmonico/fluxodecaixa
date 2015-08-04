package zero.fluxodecaixa.renderer;

import java.util.LinkedList;
import java.util.List;

import zero.easymvc.Renderer;
import zero.fluxodecaixa.model.Conta;

public class ContaListRenderer {

    private List<Conta> contas;

    @Renderer(path = { "conta", "ls" })
    public void render() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        ListPrinter printer = new ListPrinter();

        printer.setEntityName("Conta", "Contas");

        printer.setColumnDefinitions(createColumnDefinitions());

        printer.setData(contas);

        printer.print();
    }

    private List<ColumnDefinition> createColumnDefinitions() {
        List<ColumnDefinition> defs = new LinkedList<ColumnDefinition>();

        defs.add(new ColumnDefinition("ID", "id", IDFormatter.getInstance()));
        defs.add(new ColumnDefinition("Nome", "nome", StringFormatter.getInstance()));
        defs.add(new ColumnDefinition("Contabilizável", "contabilizavel", BooleanFormatter.getInstance()));

        return defs;
    }
}
