package zero.easymvc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EasyMVC {

    private Map<Command, HandlerData> handlers;
    private Map<Command, RendererData> renderers;

    public EasyMVC() {
        handlers = new HashMap<>();
        renderers = new HashMap<>();
    }

    public void registerCommandHandler(Class<?> handlerClass) {
        for (Method method : handlerClass.getMethods()) {
            CommandHandler annotation = method.getAnnotation(CommandHandler.class);

            if (annotation != null) {
                Command command = new StringArrayCommand(annotation.path());

                if (handlers.get(command) != null) {
                    throw new RuntimeException("A command can have just one handler!");
                }
                // TODO Depois fazer chamar todas as ocorrências da
                // anotação.

                HandlerData data = new HandlerData(method);

                handlers.put(command, data);
            }
        }
    }

    public void bindPathToRenderer(Class<?> rendererClass, Command command) {
        for (Method method : rendererClass.getMethods()) {
            Renderer annotation = method.getAnnotation(Renderer.class);

            if (annotation != null) {
                if (renderers.get(command) != null) {
                    throw new RuntimeException("A command can have just one renderer class!");
                }

                RendererData data = new RendererData(method);

                renderers.put(command, data);
            }
        }

    }

    public void run(String... args) throws EasyMVCException {
        run(new StringArrayCommand(args));
    }

    public void run(Command command) throws EasyMVCException {
        HandlerData handlerData = handlers.get(command);

        if (handlerData == null) {
            throw new EasyMVCException("Handler not found for command " + command.toString());
        }

        RendererData rendererData = renderers.get(command);

        if (rendererData == null) {
            throw new EasyMVCException("Renderer not found for command " + command.toString());
        }

        if (handlerData.instance == null) {
            createHandlerInstanceAndBeans(handlerData);
        }

        invokeHandler(handlerData);

        invokeRenderer(rendererData, handlerData.beans);
    }

    private void createHandlerInstanceAndBeans(HandlerData data) throws EasyMVCException {
        Method method = data.method;

        try {
            Class<?> handlerClass = method.getDeclaringClass();
            Object instance = handlerClass.newInstance();
            data.instance = instance;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new EasyMVCException(e);
        }

        Class<?>[] parameters = method.getParameterTypes();
        data.beans = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            try {
                Object bean = parameters[i].newInstance();
                data.beans[i] = bean;
            } catch (InstantiationException | IllegalAccessException e) {
                throw new EasyMVCException(e);
            }
        }
    }

    private void invokeHandler(HandlerData data) throws EasyMVCException {
        try {
            data.method.invoke(data.instance, data.beans);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new EasyMVCException(e);
        }
    }

    private void invokeRenderer(RendererData data, Object[] beans) throws EasyMVCException {
        if (data.instance == null) {
            createRendererInstance(data);
        }

        try {
            data.method.invoke(data.instance, beans);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new EasyMVCException(e);
        }
    }

    private void createRendererInstance(RendererData data) throws EasyMVCException {
        try {
            Object instance = data.method.getDeclaringClass().newInstance();

            data.instance = instance;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new EasyMVCException(e);
        }
    }

}
