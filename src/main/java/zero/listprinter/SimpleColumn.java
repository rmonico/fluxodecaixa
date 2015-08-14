package zero.listprinter;

public class SimpleColumn implements Column {

    private String title;
    private DataExtractor extractor;

    public SimpleColumn(String title, DataExtractor extractor) {
        this.title = title;
        this.extractor = extractor;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Object getData(Object line) throws ListPrinterException {
        return extractor.extract(line);
    }
}
