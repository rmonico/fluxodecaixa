package zero.fluxodecaixa.renderer;

public class ColumnDefinition {
    private String title;
    private String fieldName;
    private Formatter formatter;

    public ColumnDefinition(String title, String fieldName) {
        this(title, fieldName, null);
    }

    public ColumnDefinition(String title, String fieldName, Formatter formatter) {
        this.title = title;
        this.fieldName = fieldName;
        this.formatter = formatter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Formatter getFormatter() {
        return formatter;
    }

    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }

}
