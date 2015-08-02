package zero.easymvc;

public interface DependencyManager {

    public Class<?>[] managedClasses();

    public void beforeUse() throws Exception;

    public Object getInstance();

    public void afterUse() throws Exception;
}
