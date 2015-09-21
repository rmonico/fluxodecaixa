package zero.fluxodecaixa.renderer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import zero.easymvc.Renderer;

public class HelpRenderer {

    private List<String> text;

    @Renderer(path = "help")
    public void render() throws IOException {
        createText();

        printText();
    }

    private void createText() {
        text = new LinkedList<>();

        text.add("Fluxo de Caixa (Cash Flow) is a command line personal cash flow software. Inspired by Linux style commands, its simple, easy and versatile.");
        text.add("");
        text.add("Usage:");
        text.add("");
        text.add("  fluxodecaixa conta add <nome> [ --contabilizavel | -c ] [ --saldo | -s ] [ --observacao | -o ]");
        text.add("");
        text.add("  fluxodecaixa conta ls");
        text.add("");
        text.add("  fluxodecaixa conta rm <nome>");
        text.add("");
        text.add("  fluxodecaixa lanc add <conta origem> <conta destino> <valor> [ --observacao ] [ --data=<data> | --trans-id=<transacao id> ]");
        text.add("");
        text.add("  fluxodecaixa lanc ls");
        text.add("");
        text.add("  fluxodecaixa saldo");
        text.add("");
        text.add("Formata da data:");
        text.add("dd/mmm/yyyy");
        text.add("Nomes dos meses devem ser abreviados em inglês: Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec");
        text.add("Exemplo: 17/jul/2015");
        text.add("Também é possível usar as palavras \"anteontem\", \"ontem\", \"hoje\" e \"amanhã\"");
        text.add("");
        text.add("Formato numérico:");
        text.add("0.##");
        text.add("Exemplo 1: 1000.01");
        text.add("Exemplo 2: 10");
    }

    private void printText() {
        for (String line : text)
            System.out.println(line);
    }

}
