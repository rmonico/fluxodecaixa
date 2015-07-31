package zero.easymvc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EasyMVC {

    static final String FIRST_POSITIONAL_PARAMETER_NAME = "<initial>";
    // TODO Log stuff
    private List<CommandData> commands;
    private Map<Class<?>, DependencyManager> managers;

    public EasyMVC() {
        commands = new LinkedList<>();
        managers = new HashMap<>();
    }

    public void registerCommandHandler(Class<?> handlerClass) {
        for (Method method : handlerClass.getMethods()) {
            CommandHandler annotation = method.getAnnotation(CommandHandler.class);

            if (annotation != null) {
                Command command = new StringArrayCommand(annotation.path());

                checkHandlerParameters(method);

                checkHandlerDependencies(method.getDeclaringClass());

                // TODO Bean must have one or none "<initial>" positional
                // parameter, all positional parameter after values must be
                // other field names. Check Positional Pameter cicles
                // checkHandlerBeanSanity();

                CommandData data = getCommandDataFor(command);
                if (data == null) {
                    data = new CommandData(command);
                    commands.add(data);
                } else if (data.handlerMethod != null)
                    throw new RuntimeException("A command can have just one handler!");

                data.handlerMethod = method;
            }
        }
    }

    private void checkHandlerParameters(Method method) {
        Class<?>[] parameters = method.getParameterTypes();

        if (parameters.length != 0) {
            throw new RuntimeException("Command handlers cannot have parameters.");
        }
    }

    private void checkHandlerDependencies(Class<?> handlerClass) {
        for (Field field : handlerClass.getDeclaredFields()) {
            if (field.getAnnotation(Dependency.class) == null)
                continue;

            Class<?> dependencyClass = field.getType();

            DependencyManager dependencyManager = managers.get(dependencyClass);

            if (dependencyManager == null)
                throw new RuntimeException(String.format("Dependency \"%s\" on command \"%s\" is not manager by this controller.", field.getName(), handlerClass.getCanonicalName()));
        }
    }

    public void registerRenderer(Class<?> rendererClass) {
        for (Method method : rendererClass.getMethods()) {
            Renderer annotation = method.getAnnotation(Renderer.class);

            if (annotation != null) {
                Command command = new StringArrayCommand(annotation.path());

                CommandData data = getCommandDataFor(command);

                if (data == null) {
                    data = new CommandData(command);
                    commands.add(data);
                } else if (data.rendererMethod != null)
                    throw new RuntimeException("A command can have just one renderer class!");

                data.rendererMethod = method;
            }
        }

    }

    public List<Object> run(String... args) throws EasyMVCException {
        return run(new StringArrayCommand(args));
    }

    public List<Object> run(Command command) throws EasyMVCException {
        CommandData data = getCommandDataFor(command);

        checkCommandDataIntegrity(data, command);

        if (data.handlerInstance == null) {
            createHandlerInstance(data);
        }

        ArgumentBeanFactory beanFactory = new ArgumentBeanFactory(data, command);

        beanFactory.create();

        injectDependenciesIntoHandler(data.handlerInstance);

        invokeHandler(data);

        disposeDependencies(data.handlerInstance);

        List<Field> beanFields = getHandlerFieldBeans(data.handlerInstance);

        if (data.rendererInstance == null) {
            createRendererInstance(data);
        }

        List<Object> handlerBeans = injectHandlerBeansIntoRenderer(data, beanFields);

        invokeRenderer(data, beanFields);

        return handlerBeans;
    }

    private CommandData getCommandDataFor(Command command) {
        for (CommandData data : commands) {
            if (data.command.isSameCommand(command)) {
                return data;
            }
        }

        return null;
    }

    private void checkCommandDataIntegrity(CommandData data, Command command) throws EasyMVCException {
        if (data == null) {
            throw new EasyMVCException("Command not found: " + command.toString());
        } else if (data.rendererMethod == null) {
            throw new EasyMVCException("Renderer not found for command " + command.toString());
        } else if (data.handlerMethod == null) {
            throw new EasyMVCException("Handler data not found for command " + command.toString());
        }
    }

    private void createHandlerInstance(CommandData data) throws EasyMVCException {
        try {
            Class<?> handlerClass = data.handlerMethod.getDeclaringClass();
            Object instance = handlerClass.newInstance();
            data.handlerInstance = instance;

        } catch (InstantiationException | IllegalAccessException e) {
            throw new EasyMVCException(e);
        }
    }

    private void injectDependenciesIntoHandler(Object instance) throws EasyMVCException {
        for (Field field : instance.getClass().getDeclaredFields()) {
            if (field.getAnnotation(Dependency.class) == null)
                continue;

            Class<?> dependencyClass = field.getType();

            DependencyManager dependencyManager = managers.get(dependencyClass);

            try {
                dependencyManager.beforeUse();
            } catch (Exception e1) {
                throw new EasyMVCException(e1);
            }

            Object dependency = dependencyManager.getInstance();

            boolean accessible = field.isAccessible();

            field.setAccessible(true);
            try {
                field.set(instance, dependency);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new EasyMVCException(e);
            }

            field.setAccessible(accessible);
        }
    }

    private void invokeHandler(CommandData data) throws EasyMVCException {
        try {
            // FIXME Assumo que handlerMethod não tem parâmetros
            data.handlerMethod.invoke(data.handlerInstance);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new EasyMVCException(e);
        } catch (InvocationTargetException ite) {
            throw new EasyMVCException(ite.getCause());
        }
    }

    private void disposeDependencies(Object instance) throws EasyMVCException {
        for (Field field : instance.getClass().getDeclaredFields()) {
            if (field.getAnnotation(Dependency.class) == null)
                continue;

            Class<?> dependencyClass = field.getType();

            DependencyManager dependencyManager = managers.get(dependencyClass);

            try {
                dependencyManager.afterUse();
            } catch (Exception e) {
                throw new EasyMVCException(e);
            }
        }
    }

    private List<Field> getHandlerFieldBeans(Object handlerInstance) {
        List<Field> beans = new LinkedList<>();

        for (Field field : handlerInstance.getClass().getDeclaredFields()) {
            if (field.getAnnotation(Bean.class) == null)
                continue;

            beans.add(field);
        }

        return beans;
    }

    private void invokeRenderer(CommandData data, List<Field> beanFields) throws EasyMVCException {
        try {
            data.rendererMethod.invoke(data.rendererInstance);
            // TODO Check is renderer has no parameters
        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new EasyMVCException(e);
        } catch (InvocationTargetException ite) {
            throw new EasyMVCException(ite.getCause());
        }
    }

    private void createRendererInstance(CommandData data) throws EasyMVCException {
        try {
            Object instance = data.rendererMethod.getDeclaringClass().newInstance();

            data.rendererInstance = instance;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new EasyMVCException(e);
        }
    }

    private List<Object> injectHandlerBeansIntoRenderer(CommandData data, List<Field> beanFields) {
        List<Object> values = new LinkedList<>();

        for (Field handlerField : beanFields) {
            String fieldName = handlerField.getName();
            Field rendererField;
            try {
                rendererField = data.rendererInstance.getClass().getDeclaredField(fieldName);
            } catch (NoSuchFieldException | SecurityException e) {
                // TODO Check if renderer has corresponding handler bean fields
                throw new RuntimeException("Should never happen!", e);
            }

            boolean handlerFieldAccessible = handlerField.isAccessible();
            handlerField.setAccessible(true);
            boolean rendererFieldAccessible = rendererField.isAccessible();
            rendererField.setAccessible(true);
            Object value;
            try {
                value = handlerField.get(data.handlerInstance);

                rendererField.set(data.rendererInstance, value);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // TODO Check if renderer and handler bean classes are the same
                throw new RuntimeException("Should never happen!");
            }

            values.add(value);

            handlerField.setAccessible(handlerFieldAccessible);
            rendererField.setAccessible(rendererFieldAccessible);
        }

        return values;
    }

    public void addDependencyManager(DependencyManager manager) {
        managers.put(manager.dependencyClass(), manager);
    }

}
