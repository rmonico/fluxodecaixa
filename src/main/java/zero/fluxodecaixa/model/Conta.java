package zero.fluxodecaixa.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Conta {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String nome;

    @DatabaseField
    private boolean contabilizavel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isContabilizavel() {
        return contabilizavel;
    }

    public void setContabilizavel(boolean contabilizavel) {
        this.contabilizavel = contabilizavel;
    }

}
