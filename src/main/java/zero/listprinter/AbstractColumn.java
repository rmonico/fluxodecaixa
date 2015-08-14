package zero.listprinter;

public abstract class AbstractColumn implements Column {

    private String title;
    private Formatter formatter;

    public AbstractColumn(String title, Formatter formatter) {
        this.title = title;
        this.formatter = formatter;
    }

    @Override
    public String getTitle() {
        return title;
    }

    protected String formatData(Object theData) {
        Formatter dataFormatter;

        if (theData == null)
            dataFormatter = NullFormatter.getInstance();
        else
            dataFormatter = formatter;

        // FIXME
        return dataFormatter.format(theData).toString();
    }

}
