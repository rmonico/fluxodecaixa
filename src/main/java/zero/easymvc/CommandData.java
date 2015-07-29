package zero.easymvc;

import java.lang.reflect.Method;

class CommandData {

    Command command;
    Object handlerInstance;
    Method handlerMethod;
    Object rendererInstance;
    Method rendererMethod;

    public CommandData(Command command) {
        this.command = command;
    }
}
