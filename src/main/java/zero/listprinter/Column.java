package zero.listprinter;

public interface Column {

    public String getTitle();

    public Object getData(Object line) throws ListPrinterException;
}
