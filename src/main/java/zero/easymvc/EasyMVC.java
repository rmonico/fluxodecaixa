package zero.easymvc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

                data.handlerMethod = method;
            }
        }
    }

    private CommandData getCommandDataFor(Command command) {
        for (CommandData data : commands) {
            if (data.command.isSameCommand(command)) {
                return data;
            }
        }

        return null;
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

    public void run(String... args) throws EasyMVCException {
        run(new StringArrayCommand(args));
    }

    public void run(Command command) throws EasyMVCException {
        CommandData data = getCommandDataFor(command);

        checkCommandDataIntegrity(command, data);

        if (data.handlerInstance == null) {
            createHandlerInstanceAndBean(data);
        }

        invokeHandler(data);

        invokeRenderer(data, data.bean);
    }

    private void checkCommandDataIntegrity(Command command, CommandData data) throws EasyMVCException {
        if (data == null) {
            throw new EasyMVCException("Command not found: " + command.toString());
        } else if (data.rendererMethod == null) {
            throw new EasyMVCException("Renderer not found for command " + command.toString());
        } else if (data.handlerMethod == null) {
            throw new EasyMVCException("Handler data not found for command " + command.toString());
        }
    }

    private void createHandlerInstanceAndBean(CommandData data) throws EasyMVCException {
        Method method = data.handlerMethod;

        try {
            Class<?> handlerClass = method.getDeclaringClass();
            Object instance = handlerClass.newInstance();
            data.handlerInstance = instance;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new EasyMVCException(e);
        }

        Class<?>[] parameters = method.getParameterTypes();

        if (parameters.length > 1) {
            throw new EasyMVCException("Commands can have just one parameter.");
        }

        for (int i = 0; i < parameters.length; i++) {
            try {
                data.bean = parameters[i].newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new EasyMVCException(e);
            }
        }
    }

    private void invokeHandler(CommandData data) throws EasyMVCException {
        try {
            data.handlerMethod.invoke(data.handlerInstance, data.bean);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new EasyMVCException(e);
        }
    }

    private void invokeRenderer(CommandData data, Object bean) throws EasyMVCException {
        if (data.rendererInstance == null) {
            createRendererInstance(data);
        }

        try {
            data.rendererMethod.invoke(data.rendererInstance, bean);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new EasyMVCException(e);
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
