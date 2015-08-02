package zero.easymvc;

public interface DependencyManager {

    public Class<?>[] managedClasses();

    public void beforeUse(Class<?> dependencyClass) throws Exception;

    public Object getInstance();

    public void afterUse(Class<?> dependencyClass) throws Exception;
}
