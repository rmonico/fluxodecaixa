package zero.fluxodecaixa.model;

import zero.fluxodecaixa.app.dao.MetaInfDao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(daoClass = MetaInfDao.class)
public class MetaInf {
    public static final String ID_FIELD_NAME = "id";
    public static final String KEY_FIELD_NAME = "key";
    public static final String VALUE_FIELD_NAME = "value";
    public static final String TABLE_NAME = "metainf";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String key;

    @DatabaseField
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
