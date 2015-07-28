package zero.easymvc;

public interface DependencyManager {

    public void beforeUse();

    public Object getInstance();

    public void afterUse();
}
