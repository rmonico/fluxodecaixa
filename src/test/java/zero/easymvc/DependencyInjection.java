package zero.easymvc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DependencyInjection {

    public static class TheDependency {
        public boolean configured;
        public boolean used;
        public boolean disposed;

    }

    public static class DependencyManagerImpl implements DependencyManager {

        private TheDependency dependency;

        public DependencyManagerImpl() {
            dependency = new TheDependency();
        }

        @Override
        public Class<?> dependencyClass() {
            return TheDependency.class;
        }

        @Override
        public void beforeUse() {
            dependency.configured = true;
        }

        @Override
        public TheDependency getInstance() {
            return dependency;
        }

        @Override
        public void afterUse() {
            dependency.disposed = true;
        }

    }

    public static class Command {
        @Dependency
        private TheDependency dependency;

        @CommandHandler(path = { "command" })
        public void execute() {
            dependency.used = true;
        }

        @Renderer
        public void render() {

        }

    }

    @Test
    public void should_inject_dependency_by_annotation() throws EasyMVCException {
        EasyMVC controller = new EasyMVC();

        DependencyManagerImpl dependencyManager = new DependencyManagerImpl();

        controller.addDependencyManager(dependencyManager);

        controller.registerCommandHandler(Command.class);

        controller.bindPathToRenderer(Command.class, new StringArrayCommand("command"));

        controller.run("command");

        TheDependency dependency = dependencyManager.getInstance();

        assertNotNull(dependency);
        assertTrue(dependency.configured);
        assertTrue(dependency.used);
        assertTrue(dependency.disposed);
    }
}
