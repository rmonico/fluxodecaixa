package zero.fluxodecaixa.renderer;

public interface Column {

    public String getTitle();

    public String getData(Object line) throws ListPrinterException;
}
