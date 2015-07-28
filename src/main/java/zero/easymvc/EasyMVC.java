package zero.easymvc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EasyMVC {

    private List<CommandData> commands;

    public EasyMVC() {
        commands = new LinkedList<>();
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

        invokeHandler(data);

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

        Class<?> beanClass = data.handlerMethod.getParameterTypes()[0];

        List<Field> fields = getBeanInjectedFields(beanClass);

        if (extraArgs.length > fields.size())
            throw new EasyMVCException("Extra arguments found. Command: " + command.toString());
        else if (extraArgs.length < fields.size()) {
            throw new EasyMVCException("Insuficient arguments. Command: " + command.toString());
        }

        try {
            data.bean = beanClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new EasyMVCException(e);
        }

        injectExtraArgsIntoBeanFields(data.bean, fields, extraArgs);
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

            try {
                field.setAccessible(true);
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

    private void invokeHandler(CommandData data) throws EasyMVCException {
        try {
            data.handlerMethod.invoke(data.handlerInstance, data.bean);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new EasyMVCException(e);
        } catch (InvocationTargetException ite) {
            throw new EasyMVCException(ite.getCause());
        }
    }

    private void invokeRenderer(CommandData data, Object bean) throws EasyMVCException {
        if (data.rendererInstance == null) {
            createRendererInstance(data);
        }

        try {
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

}
