package zero.listprinter;

import java.lang.reflect.Field;

public class ReflectionFieldExtractor implements DataExtractor {

    private String fieldName;
    private DataExtractor subExtractor;

    public ReflectionFieldExtractor(String fieldName) {
        this(fieldName, null);
    }

    public ReflectionFieldExtractor(String fieldName, DataExtractor subExtractor) {
        this.fieldName = fieldName;
        this.subExtractor = subExtractor;
    }

    @Override
    public Object extract(Object line) throws ListPrinterException {
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

        if (subExtractor != null)
            return subExtractor.extract(theData);
        else
            return theData;
    }

}
