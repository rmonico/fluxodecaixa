package zero.listprinter;

public interface DataExtractor {

    Object extract(Object line) throws ListPrinterException;

}
