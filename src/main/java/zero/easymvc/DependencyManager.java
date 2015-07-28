package zero.easymvc;

public interface DependencyManager {

    public Class<?> dependencyClass();

    public void beforeUse();

    public Object getInstance();

    public void afterUse();
}
