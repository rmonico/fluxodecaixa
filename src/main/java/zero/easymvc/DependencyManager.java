package zero.easymvc;

public interface DependencyManager {

    public Class<?>[] managedClasses();

    public Object getInstance(Class<?> dependencyClass) throws Exception;

    public void afterUse(Class<?> dependencyClass) throws Exception;
}
