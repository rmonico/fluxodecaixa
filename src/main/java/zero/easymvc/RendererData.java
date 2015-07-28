package zero.easymvc;

import java.lang.reflect.Method;

public class RendererData {

    public Object instance;
    public Method method;

    public RendererData(Method method) {
        this.method = method;
    }

}
