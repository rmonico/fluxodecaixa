package zero.easymvc;

public interface DependencyManager {

    public Class<?>[] dependencyClass();

    public void beforeUse() throws Exception;

    public Object getInstance();

    public void afterUse() throws Exception;
}
