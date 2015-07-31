package zero.easymvc;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class ArgumentBeanFactory {

    private CommandData data;
    private Command command;
    private Object[] args;
    private List<Field> requiredFields;
    private List<Field> optionalFields;

    public ArgumentBeanFactory(CommandData data, Command command) {
        this.data = data;
        this.command = command;
    }

    public void create() throws EasyMVCException {
        populateArgs();

        // TODO Check if handler has more than one @ArgumentBean annotation
        Field beanField = getArgumentBeanClass();

        if (beanField == null)
            if (args.length > 0)
                // TODO Show which are the extra arguments
                throw new EasyMVCException("Extra arguments found. Command: " + command.toString());
            else
                return;

        populateRequiredFields(beanField.getType());

        sortRequiredFields();

        if (!isAllRequiredArgumentsOnCommand()) {
            throw new EasyMVCException("Insuficient arguments. Command: " + command.toString());
        }

        populateOptionalFields(beanField.getType());

        Object bean;
        try {
            bean = beanField.getType().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new EasyMVCException(e);
        }

        injectRequiredArguments(bean);
        injectOptionalArguments(bean);

        injectArgumentBeanIntoHandler(beanField, bean);
    }

    private void populateArgs() {
        Object[] fullCommand = command.args();
        Object[] dataCommandArgs = data.command.args();
        int argCount = fullCommand.length - dataCommandArgs.length;

        args = Arrays.copyOfRange(fullCommand, fullCommand.length - argCount, fullCommand.length);
    }

    private Field getArgumentBeanClass() {
        for (Field field : data.handlerInstance.getClass().getDeclaredFields()) {
            if (field.getAnnotation(ArgumentsBean.class) != null) {
                return field;
            }
        }

        return null;
    }

    private void populateRequiredFields(Class<?> beanClass) {
        requiredFields = new LinkedList<>();

        for (Field field : beanClass.getDeclaredFields()) {
            PositionalParameter annotation = field.getAnnotation(PositionalParameter.class);

            if ((annotation == null) || (!annotation.required())) {
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

            BeanParser parser = getBeanParserForField(field);

            Object beanValue;

            try {
                beanValue = parser.parse(args[i]);
            } catch (BeanParserException e) {
                Object handlerInstance = data.handlerInstance;
                String message = String.format("Error parsing value \"%s\" for bean \"%s\" on handler \"%s\".", args[i].toString(), field.getName(), handlerInstance.getClass().getCanonicalName());
                throw new EasyMVCException(message, e);
            }

            try {
                field.set(bean, beanValue);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                Object handlerInstance = data.handlerInstance;
                String message = String.format("Error setting value of bean \"%s\" on handler \"%s\".", field.getName(), handlerInstance.getClass().getCanonicalName());
                throw new EasyMVCException(message, e);
            }
        }
    }

    private BeanParser getBeanParserForField(Field field) throws EasyMVCException {
        PositionalParameter annotation = field.getAnnotation(PositionalParameter.class);

        Class<? extends BeanParser> beanParserClass = annotation.parser();

        Class<?> beanParserClassInstance = null;
        try {
            beanParserClassInstance = Class.forName(BeanParser.class.getCanonicalName());
        } catch (ClassNotFoundException e1) {
            // Will never happen, JVM exception?
        }

        if (beanParserClassInstance.equals(beanParserClass)) {
            return getBuiltinBeanParser(field);
        } else {
            try {
                // TODO Check if beanParserClass has a default constructor
                return beanParserClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new EasyMVCException("Error creating bean parser.", e);
            }
        }
    }

    private BeanParser getBuiltinBeanParser(Field field) throws EasyMVCException {
        Class<?> fieldClass = field.getType();

        BeanParser beanParser = BuiltinParsers.parsers.get(fieldClass);

        if (beanParser != null)
            return beanParser;

        Object handlerInstance = data.handlerInstance;
        String message = String.format("BeanParser not found for field \"%s\" (class \"%s\") on handler \"%s\".", field.getName(), fieldClass.getCanonicalName(), handlerInstance.getClass().getCanonicalName());
        throw new EasyMVCException(message);
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

    private void injectArgumentBeanIntoHandler(Field field, Object bean) throws EasyMVCException {
        boolean accessible = field.isAccessible();
        field.setAccessible(true);

        try {
            field.set(data.handlerInstance, bean);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new EasyMVCException(e);
        }

        field.setAccessible(accessible);
    }

}
