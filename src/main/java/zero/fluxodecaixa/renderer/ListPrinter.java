package zero.fluxodecaixa.renderer;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class ListPrinter {

    private List<ColumnDefinition> columnDefinitions;
    private List<?> dataList;
    private List<Integer> columnWidths;
    private String entityName;
    private String pluralEntityName;

    public void setEntityName(String entityName, String pluralEntityName) {
        this.entityName = entityName;
        this.pluralEntityName = pluralEntityName;
    }

    public void setColumnDefinitions(List<ColumnDefinition> columnDefinitions) {
        this.columnDefinitions = columnDefinitions;
    }

    public void setData(List<?> dataList) {
        this.dataList = dataList;
    }

    public void print() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        List<List<StringBuilder>> formattedData = formatData();

        calculateWidths(formattedData);

        printEverything(formattedData);
    }

    private List<List<StringBuilder>> formatData() throws NoSuchFieldException, IllegalAccessException {
        List<List<StringBuilder>> formattedDataList = new LinkedList<List<StringBuilder>>();

        List<StringBuilder> headerLine = new LinkedList<StringBuilder>();
        for (ColumnDefinition columnDefinition : columnDefinitions)
            headerLine.add(new StringBuilder(columnDefinition.getTitle()));
        formattedDataList.add(headerLine);

        for (Object line : dataList) {
            List<StringBuilder> formattedLine = new LinkedList<StringBuilder>();

            for (ColumnDefinition columnDefinition : columnDefinitions) {
                Field field = line.getClass().getDeclaredField(columnDefinition.getFieldName().toString());

                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                Object theData = field.get(line);
                field.setAccessible(accessible);

                Formatter formatter;

                if (theData == null)
                    formatter = NullFormatter.getInstance();
                else
                    formatter = columnDefinition.getFormatter();

                StringBuilder stringData = formatter.format(theData);

                formattedLine.add(stringData);
            }

            formattedDataList.add(formattedLine);
        }

        return formattedDataList;
    }

    private void calculateWidths(List<List<StringBuilder>> formattedData) {
        List<Integer> widths = new LinkedList<Integer>();

        for (int i = 0; i < formattedData.get(0).size(); i++) {
            widths.add(-1);
        }

        for (List<StringBuilder> line : formattedData) {
            for (int columnIndex = 0; columnIndex < line.size(); columnIndex++) {
                StringBuilder column = line.get(columnIndex);

                int dataLength = column.length();

                if (dataLength > widths.get(columnIndex)) {
                    widths.remove(columnIndex);
                    widths.add(columnIndex, dataLength);
                }
            }
        }

        columnWidths = widths;
    }

    private void printEverything(List<List<StringBuilder>> formattedData) {
        boolean headerPrinted = false;

        for (List<StringBuilder> line : formattedData) {
            if (!headerPrinted) {
                printHeader(line);
                headerPrinted = true;
            } else
                printBody(line);
        }

        printRuler();

        System.out.println();

        int bodyLineCount = formattedData.size() - 1;

        String entity = bodyLineCount == 1 ? entityName : pluralEntityName;

        System.out.println(String.format("%d %s", bodyLineCount, entity));
    }

    private void printHeader(List<StringBuilder> line) {
        printRuler();

        StringBuilder stringLine = createBodyString(line);

        System.out.println(stringLine.toString());

        printRuler();
    }

    private void printRuler() {
        StringBuilder ruler = new StringBuilder();

        ruler.append("+");

        for (int i = 0; i < columnWidths.size(); i++) {
            int width = columnWidths.get(i);

            appendRepeatedString(ruler, "-", width + 2);

            ruler.append("+");
        }

        System.out.println(ruler);
    }

    private void appendRepeatedString(StringBuilder builder, String string, int count) {
        for (int i = 0; i < count; i++) {
            builder.append(string);
        }
    }

    private void printBody(List<StringBuilder> line) {
        StringBuilder stringLine = createBodyString(line);

        System.out.println(stringLine.toString());
    }

    private StringBuilder createBodyString(List<StringBuilder> line) {
        StringBuilder stringLine = new StringBuilder();

        stringLine.append("|");

        for (int i = 0; i < line.size(); i++) {
            StringBuilder data = line.get(i);

            int width = columnWidths.get(i);

            stringLine.append(" ");

            stringLine.append(data);

            appendRepeatedString(stringLine, " ", width - data.length());

            stringLine.append(" |");
        }

        return stringLine;
    }

}