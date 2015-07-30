package zero.fluxodecaixa.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Lancamento {

    public static final String ID_FIELD_NAME = "id";
    public static final String TRANSACAO_FIELD_NAME = "transacaoid";
    public static final String ORIGEM_FIELD_NAME = "origemid";
    public static final String DESTINO_FIELD_NAME = "destinoid";
    public static final String VALOR_FIELD_NAME = "valor";
    public static final String OBSERVACAO_FIELD_NAME = "observacao";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, canBeNull = true)
    private Transacao transacao;

    @DatabaseField(foreign = true)
    private Conta origem;

    @DatabaseField(foreign = true)
    private Conta destino;

    @DatabaseField
    private double valor;

    @DatabaseField
    private String observacao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Transacao getTransacao() {
        return transacao;
    }

    public void setTransacao(Transacao transacao) {
        this.transacao = transacao;
    }

    public Conta getOrigem() {
        return origem;
    }

    public void setOrigem(Conta origem) {
        this.origem = origem;
    }

    public Conta getDestino() {
        return destino;
    }

    public void setDestino(Conta destino) {
        this.destino = destino;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

}
