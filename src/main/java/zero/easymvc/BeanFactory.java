package zero.easymvc;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class BeanFactory {

    private CommandData data;
    private Command command;
    private Object[] args;
    private List<Field> requiredFields;
    private List<Field> optionalFields;

    public BeanFactory(CommandData data, Command command) {
        this.data = data;
        this.command = command;
    }

    public Object create() throws EasyMVCException {
        populateArgs();

        Class<?>[] parameterTypes = data.handlerMethod.getParameterTypes();

        boolean hasBean = parameterTypes.length > 0;

        if (!hasBean)
            if (args.length > 0)
                // TODO Show which are the extra arguments
                throw new EasyMVCException("Extra arguments found. Command: " + command.toString());
            else
                return null;

        Class<?> beanClass = parameterTypes[0];

        populateRequiredFields(beanClass);

        sortRequiredFields();

        if (!isAllRequiredArgumentsOnCommand()) {
            throw new EasyMVCException("Insuficient arguments. Command: " + command.toString());
        }

        populateOptionalFields(beanClass);

        Object bean;
        try {
            bean = beanClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new EasyMVCException(e);
        }

        injectRequiredArguments(bean);
        injectOptionalArguments(bean);

        return bean;
    }

    private void populateArgs() {
        Object[] fullCommand = command.args();
        Object[] dataCommandArgs = data.command.args();
        int argCount = fullCommand.length - dataCommandArgs.length;

        args = Arrays.copyOfRange(fullCommand, fullCommand.length - argCount, fullCommand.length);
    }

    private void populateRequiredFields(Class<?> beanClass) {
        requiredFields = new LinkedList<>();

        for (Field field : beanClass.getDeclaredFields()) {
            if (field.getAnnotation(PositionalParameter.class) == null) {
                continue;
            }

            requiredFields.add(field);
        }
    }

    private void sortRequiredFields() {
        String previousFieldName = EasyMVC.FIRST_POSITIONAL_PARAMETER_NAME;

        for (int i = 0; i < requiredFields.size(); i++) {
            for (int j = i; j < requiredFields.size(); j++) {
                Field field = requiredFields.get(j);

                PositionalParameter annotation = field.getAnnotation(PositionalParameter.class);

                if (annotation == null)
                    continue;

                if (annotation.after().equals(previousFieldName)) {
                    requiredFields.remove(field);
                    requiredFields.add(i, field);

                    previousFieldName = field.getName();
                    break;
                }
            }
        }
    }

    private boolean isAllRequiredArgumentsOnCommand() {
        return requiredFields.size() <= args.length;
    }

    private void populateOptionalFields(Class<?> beanClass) throws EasyMVCException {
        List<Field> fields = getFlagParameterFields(beanClass);

        optionalFields = new LinkedList<>();

        for (int i = requiredFields.size(); i < args.length; i++) {
            Object o = args[i];

            // FIXME Ignoring non-string arguments
            if (!(o instanceof String)) {
                continue;
            }

            String arg = (String) o;

            // TODO Check if arg is used more than once
            if (!isArgumentValid(fields, arg)) {
                throw new EasyMVCException(String.format("Invalid argument \"%s\".", arg));
            }
        }
    }

    private List<Field> getFlagParameterFields(Class<?> beanClass) {
        List<Field> fields = new LinkedList<Field>();

        for (Field field : beanClass.getDeclaredFields()) {
            FlagParameter annotation = field.getAnnotation(FlagParameter.class);

            if (annotation == null) {
                continue;
            }

            fields.add(field);
        }
        return fields;
    }

    private boolean isArgumentValid(List<Field> fields, String arg) {
        for (Field optional : fields) {
            FlagParameter annotation = optional.getAnnotation(FlagParameter.class);

            for (String token : annotation.token()) {
                if (token.equals(arg)) {
                    optionalFields.add(optional);

                    return true;
                }
            }
        }

        return false;
    }

    private void injectRequiredArguments(Object bean) throws EasyMVCException {
        for (int i = 0; i < requiredFields.size(); i++) {
            Field field = requiredFields.get(i);

            if (!field.isAccessible())
                field.setAccessible(true);

            try {
                field.set(bean, args[i]);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new EasyMVCException(e);
            }
        }
    }

    private void injectOptionalArguments(Object bean) throws EasyMVCException {
        int requiredFieldsSize = requiredFields.size();
        for (int i = requiredFieldsSize; i < requiredFieldsSize + optionalFields.size(); i++) {
            Field field = optionalFields.get(i - requiredFieldsSize);

            if (!field.isAccessible())
                field.setAccessible(true);

            try {
                field.setBoolean(bean, true);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new EasyMVCException(e);
            }
        }
    }

}
