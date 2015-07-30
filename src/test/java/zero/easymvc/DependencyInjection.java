package zero.easymvc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DependencyInjection extends AbstractEasyMVCTest {

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

    @Test(expected = RuntimeException.class)
    public void should_throw_exception_when_a_commands_dependes_on_a_unmanaged_dependency() {
        controller.registerCommandHandler(Command.class);
    }

    @Test
    public void should_inject_dependency_by_annotation() throws EasyMVCException {
        DependencyManagerImpl dependencyManager = new DependencyManagerImpl();

        controller.addDependencyManager(dependencyManager);

        controller.registerCommandHandler(Command.class);

        controller.registerRenderer(Command.class, new StringArrayCommand("command"));

        controller.run("command");

        TheDependency dependency = dependencyManager.getInstance();

        assertNotNull(dependency);
        assertTrue(dependency.configured);
        assertTrue(dependency.used);
        assertTrue(dependency.disposed);
    }
}
