package zero.listprinter;

public abstract class AbstractColumn implements Column {

    private String title;

    public AbstractColumn(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }

}
