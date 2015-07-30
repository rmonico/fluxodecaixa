package zero.fluxodecaixa.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Transacao {

    public static final String ID_FIELD_NAME = "id";
    public static final String DATA_FIELD_NAME = "data";
    public static final String OBSERVACAO_FIELD_NAME = "observacao";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(dataType = DataType.DATE_STRING)
    private Date data;

    @DatabaseField
    private String observacao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getData() {
        Calendar calendar = GregorianCalendar.getInstance();

        calendar.setTime(data);

        return calendar;
    }

    public void setData(Calendar data) {
        this.data = data.getTime();
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public static String getIdFieldName() {
        return ID_FIELD_NAME;
    }

    public static String getDataFieldName() {
        return DATA_FIELD_NAME;
    }

    public static String getObservacaoFieldName() {
        return OBSERVACAO_FIELD_NAME;
    }

}
