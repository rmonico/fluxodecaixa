package zero.listprinter;

public class FormattedColumn extends SimpleColumn {

    private Formatter formatter;

    public FormattedColumn(String title, DataExtractor extractor, Formatter formatter) {
        super(title, extractor);

        this.formatter = formatter;
    }

    @Override
    public String getData(Object line) throws ListPrinterException {
        Object theData = super.getData(line);

        return formatData(theData);
    }

    protected String formatData(Object theData) {
        Formatter dataFormatter;

        if (theData == null)
            dataFormatter = NullFormatter.getInstance();
        else
            dataFormatter = formatter;

        return dataFormatter.format(theData);
    }

}
