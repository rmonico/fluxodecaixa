package zero.listprinter;

import java.lang.reflect.Field;

public class ReflectionFieldColumn extends AbstractColumn {

    private String fieldName;

    public ReflectionFieldColumn(String title, String fieldName, Formatter formatter) {
        super(title, formatter);

        this.fieldName = fieldName;
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

        return formatData(theData);
    }

}
