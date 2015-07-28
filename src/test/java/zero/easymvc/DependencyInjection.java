package zero.easymvc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DependencyInjection {

    public static class Dependency {
        public boolean configured;
        public boolean used;
        public boolean disposed;

    }

    public static class DependencyManagerImpl implements DependencyManager {

        private Dependency dependency;

        public DependencyManagerImpl() {
            dependency = new Dependency();
        }

        @Override
        public void beforeUse() {
            dependency.configured = true;
        }

        @Override
        public Dependency getInstance() {
            return dependency;
        }

        @Override
        public void afterUse() {
            dependency.disposed = true;
        }

    }

    public static class Command {
        @Depedency
        private Dependency dependency;

        @CommandHandler(path = { "command" })
        public void execute() {
            dependency.used = true;
        }

    }

    @Test
    public void should_inject_dependency_by_annotation() {
        EasyMVC controller = new EasyMVC();

        DependencyManagerImpl dependencyManager = new DependencyManagerImpl();

        controller.addDependencyManager(dependencyManager);

        controller.registerCommandHandler(Command.class);

        Dependency dependency = dependencyManager.getInstance();

        assertNotNull(dependency);
        assertTrue(dependency.configured);
        assertTrue(dependency.used);
        assertTrue(dependency.disposed);
    }
}
