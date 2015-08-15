package zero.fluxodecaixa.renderer;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import zero.easymvc.Bean;
import zero.easymvc.Renderer;
import zero.fluxodecaixa.model.Saldo;
import zero.listprinter.Column;
import zero.listprinter.DateFormatter;
import zero.listprinter.DateToCalendarConversor;
import zero.listprinter.FormattedColumn;
import zero.listprinter.ListPrinter;
import zero.listprinter.ListPrinterException;
import zero.listprinter.MapExtractor;
import zero.listprinter.MoneyFormatter;
import zero.listprinter.ReflectionFieldExtractor;

public class SaldoRenderer {

    @Bean
    private List<Saldo> saldos;
    private Set<String> nomeContas;

    @Renderer(path = { "saldo" })
    public void render() throws ListPrinterException {
        ListPrinter printer = new ListPrinter();

        printer.setEntityName("Dia", "Dias");

        printer.setColumnDefinitions(createColumns());

        printer.setData(saldos);

        printer.print();
    }

    private List<Column> createColumns() {
        createAllContas();

        List<Column> columns = new LinkedList<>();

        columns.add(new FormattedColumn("Data", new ReflectionFieldExtractor("data", new DateToCalendarConversor()), DateFormatter.getInstance()));

        for (String nomeConta : nomeContas)
            columns.add(new FormattedColumn(nomeConta, new ReflectionFieldExtractor("valores", new MapExtractor(nomeConta)), MoneyFormatter.getInstance()));

        columns.add(new FormattedColumn("Total", new ReflectionFieldExtractor("valores", new TotalSaldoCalculator()), MoneyFormatter.getInstance()));

        return columns;
    }

    private void createAllContas() {
        nomeContas = new HashSet<>();

        for (Saldo saldo : saldos)
            nomeContas.addAll(saldo.getValores().keySet());
    }

}
