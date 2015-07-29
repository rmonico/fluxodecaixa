package zero.fluxodecaixa;

import java.util.List;

import zero.easymvc.Renderer;
import zero.fluxodecaixa.app.ContaLsBean;
import zero.fluxodecaixa.model.Conta;

public class ContaLsRenderer {

    // TODO Extract a list printer
    private final String ID_HEADER = "ID";
    private final String NOME_HEADER = "Nome";
    private final String CONTABILIZAVEL_HEADER = "Contabilizável";

    @Renderer
    public void render(ContaLsBean bean) {
        List<Conta> contas = bean.getContas();
        int idWidth = getIdWidth(contas);
        int nomeWidth = getNomeWidth(contas);
        int contabilizavelWidth = getContabilizavelWidth();

        String horizontalRule = buildHorizontalRule(idWidth, nomeWidth, contabilizavelWidth);
        System.out.println(horizontalRule);
        System.out.println(buildDataLine(idWidth, ID_HEADER, nomeWidth, NOME_HEADER, contabilizavelWidth, CONTABILIZAVEL_HEADER));
        System.out.println(horizontalRule);

        for (Conta conta : contas) {
            StringBuilder sb = buildDataLine(idWidth, formatId(conta.getId()), nomeWidth, conta.getNome(), contabilizavelWidth, formatContabilizavel(conta.isContabilizavel()));

            System.out.println(sb.toString());
        }

        System.out.println(horizontalRule);
        System.out.println();
        System.out.println(String.format("%d contas", contas.size()));
    }

    private StringBuilder buildDataLine(int idWidth, String id, int nomeWidth, String nome, int contabilizavelWidth, String contabilizavel) {
        StringBuilder sb = new StringBuilder();

        sb.append("| ");
        sb.append(id);
        sb.append(repeatString(" ", idWidth - id.length() - 2));
        sb.append(" ");

        sb.append("| ");
        sb.append(nome);
        sb.append(repeatString(" ", nomeWidth - nome.length() - 2));
        sb.append(" ");

        sb.append("| ");
        sb.append(contabilizavel);
        sb.append(repeatString(" ", contabilizavelWidth - contabilizavel.length() - 2));
        sb.append(" ");

        sb.append("|");
        return sb;
    }

    private int getContabilizavelWidth() {
        String isContabilizavel = formatContabilizavel(true);
        String isNotContabilizavel = formatContabilizavel(false);

        int contabilizavelWidth;

        if (isContabilizavel.length() > isNotContabilizavel.length())
            contabilizavelWidth = isContabilizavel.length();
        else
            contabilizavelWidth = isNotContabilizavel.length();

        if (CONTABILIZAVEL_HEADER.length() > contabilizavelWidth) {
            contabilizavelWidth = CONTABILIZAVEL_HEADER.length();
        }

        return contabilizavelWidth + 2;
    }

    private String formatContabilizavel(boolean contabilizavel) {
        return contabilizavel ? "true" : "false";
    }

    private String buildHorizontalRule(int idWidth, int nomeWidth, int contabilizavelWidth) {
        StringBuilder header = new StringBuilder();

        header.append("+");
        header.append(repeatString("-", idWidth));
        header.append("+");
        header.append(repeatString("-", nomeWidth));
        header.append("+");
        header.append(repeatString("-", contabilizavelWidth));
        header.append("+");

        return header.toString();
    }

    private StringBuilder repeatString(String string, int count) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < count; i++) {
            sb.append(string);
        }

        return sb;
    }

    private int getIdWidth(List<Conta> contas) {
        int width = 0;

        for (Conta conta : contas) {
            String id = formatId(conta.getId());

            if (id.length() > width)
                width = id.length();
        }

        if (ID_HEADER.length() > width)
            width = ID_HEADER.length();

        return width + 2;
    }

    private String formatId(int id) {
        return "#" + Integer.toString(id);
    }

    private int getNomeWidth(List<Conta> contas) {
        int width = 0;

        for (Conta conta : contas) {
            String nome = conta.getNome();

            if (nome.length() > width)
                width = nome.length();
        }

        if (NOME_HEADER.length() > width)
            width = NOME_HEADER.length();

        return width + 2;
    }

}
