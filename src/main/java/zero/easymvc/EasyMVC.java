package zero.easymvc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EasyMVC {

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

                CommandData data = getCommandDataFor(command);
                if (data == null) {
                    data = new CommandData(command);
                    commands.add(data);
                } else if (data.handlerMethod != null)
                    // TODO Depois fazer chamar todas as ocorrências da
                    // anotação.
                    throw new RuntimeException("A command can have just one handler!");

                Class<?>[] parameters = method.getParameterTypes();

                if (parameters.length > 1) {
                    throw new RuntimeException("Command handlers can have just one parameter.");
                }

                data.handlerMethod = method;
            }
        }
    }

    public void bindPathToRenderer(Class<?> rendererClass, Command command) {
        for (Method method : rendererClass.getMethods()) {
            Renderer annotation = method.getAnnotation(Renderer.class);

            if (annotation != null) {
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

    public Object run(String... args) throws EasyMVCException {
        return run(new StringArrayCommand(args));
    }

    public Object run(Command command) throws EasyMVCException {
        CommandData data = getCommandDataFor(command);

        checkCommandDataIntegrity(data, command);

        createBean(data, command);

        if (data.handlerInstance == null) {
            createHandlerInstance(data);
        }

        injectDependenciesIntoHandler(data.handlerInstance);

        invokeHandler(data);

        disposeDependencies(data.handlerInstance);

        invokeRenderer(data, data.bean);

        return data.bean;
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

    private void createBean(CommandData data, Command command) throws EasyMVCException {
        Object[] extraArgs = getExtraArgs(data, command);

        Class<?>[] parameterTypes = data.handlerMethod.getParameterTypes();

        List<Field> fields;

        boolean hasBean = parameterTypes.length > 0;
        Class<?> beanClass;

        if (hasBean) {
            beanClass = parameterTypes[0];

            fields = getBeanInjectedFields(beanClass);
        } else {
            beanClass = null;

            fields = new LinkedList<>();
        }

        if (extraArgs.length > fields.size())
            throw new EasyMVCException("Extra arguments found. Command: " + command.toString());
        else if (extraArgs.length < fields.size()) {
            throw new EasyMVCException("Insuficient arguments. Command: " + command.toString());
        }

        if (hasBean) {
            try {
                data.bean = beanClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new EasyMVCException(e);
            }

            injectExtraArgsIntoBeanFields(data.bean, fields, extraArgs);
        }
    }

    private Object[] getExtraArgs(CommandData data, Command command) {
        Object[] commandArgs = command.args();
        Object[] dataCommandArgs = data.command.args();
        int extraArgCount = commandArgs.length - dataCommandArgs.length;

        Object[] extraArgs = Arrays.copyOfRange(commandArgs, commandArgs.length - extraArgCount, commandArgs.length);

        return extraArgs;
    }

    private List<Field> getBeanInjectedFields(Class<?> beanClass) throws EasyMVCException {
        List<Field> fields = new LinkedList<>();

        for (Field field : beanClass.getDeclaredFields()) {
            if (field.getAnnotation(Parameter.class) != null)
                fields.add(field);
        }

        return fields;
    }

    private void injectExtraArgsIntoBeanFields(Object bean, List<Field> fields, Object[] extraArgs) throws EasyMVCException {
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);

            field.setAccessible(true);

            try {
                field.set(bean, extraArgs[i]);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new EasyMVCException(e);
            }
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

            dependencyManager.beforeUse();

            Object dependency = dependencyManager.getInstance();

            field.setAccessible(true);
            try {
                field.set(instance, dependency);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new EasyMVCException(e);
            }
        }
    }

    private void invokeHandler(CommandData data) throws EasyMVCException {
        try {
            if (data.bean == null)
                data.handlerMethod.invoke(data.handlerInstance);
            else
                data.handlerMethod.invoke(data.handlerInstance, data.bean);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new EasyMVCException(e);
        } catch (InvocationTargetException ite) {
            throw new EasyMVCException(ite.getCause());
        }
    }

    private void disposeDependencies(Object instance) {
        for (Field field : instance.getClass().getDeclaredFields()) {
            if (field.getAnnotation(Dependency.class) == null)
                continue;

            Class<?> dependencyClass = field.getType();

            DependencyManager dependencyManager = managers.get(dependencyClass);

            dependencyManager.afterUse();
        }
    }

    private void invokeRenderer(CommandData data, Object bean) throws EasyMVCException {
        if (data.rendererInstance == null) {
            createRendererInstance(data);
        }

        try {
            if (bean == null)
                data.rendererMethod.invoke(data.rendererInstance);
            else
                data.rendererMethod.invoke(data.rendererInstance, bean);
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

    public void addDependencyManager(DependencyManager manager) {
        managers.put(manager.dependencyClass(), manager);
    }

}
