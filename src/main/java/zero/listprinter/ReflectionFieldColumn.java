package zero.listprinter;

import java.lang.reflect.Field;

public class ReflectionFieldColumn implements Column {
    private String title;
    private String fieldName;
    private Formatter formatter;

    public ReflectionFieldColumn(String title, String fieldName, Formatter formatter) {
        this.title = title;
        this.fieldName = fieldName;
        this.formatter = formatter;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getData(Object line) throws ListPrinterException {
        Field field;
        try {
            field = line.getClass().getDeclaredField(fieldName.toString());
        } catch (NoSuchFieldException | SecurityException e) {
            throw new ListPrinterException(e);
        }

        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        Object theData;
        try {
            theData = field.get(line);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new ListPrinterException(e);
        }
        field.setAccessible(accessible);

        Formatter dataFormatter;

        if (theData == null)
            dataFormatter = NullFormatter.getInstance();
        else
            dataFormatter = formatter;

        // FIXME
        return dataFormatter.format(theData).toString();
    }

}
