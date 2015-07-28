package zero.easymvc;

import java.lang.reflect.Method;

class HandlerData {

    public Object instance;
    public Method method;
    public Object bean;

    public HandlerData(Method method) {
        this.method = method;
    }

    public CommandHandler getAnnotation() {
        return method.getAnnotation(CommandHandler.class);
    }
}
