package handlers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Abstract visitor viewer, base on Visitor pattern, implements Handler functional interface
 *
 * @param <T> - generic type of processed object
 * @param <R> - generic type of received object
 */
public class AbstractVisitorViewer<R, T> implements Handler<R, T> {

    /**
     * a value map that associates the type of an object with a method that processes the object
     */
    private Map<String, Method> methodList = new HashMap<>();
    /**
     * a set containing all types of objects that should be processed by the standard handler
     */
    private Set<String> defaultHandles = new HashSet<>();
    /**
     * sublayer whose method will be called if no method is found to process the current object in the current layer
     */
    private Handler<R, T> subHandler;

    private boolean invocationSuitable = false;

    /**
     * Method to get facade sub handler
     *
     * @return Facade sublayer with type {@link Handler}
     */
    public Handler<R, T> getSubHandler() {
        return subHandler;
    }

    /**
     * Method to set facade sub handler
     *
     * @param subHandler - sublayer, whose method will be called if no method is found to process the current object in the current layer
     */
    public void setSubHandler(Handler<R, T> subHandler) {
        this.subHandler = subHandler;
    }

    /**
     * Base constructor
     *
     * @param subHandler - sublayer, whose method will be called if no method is found to process the current object in the current layer
     */
    public AbstractVisitorViewer(Handler<R, T> subHandler) {
        this(subHandler, false);
    }

    public AbstractVisitorViewer(Handler<R, T> subHandler, boolean invocationSuitable) {
        this.subHandler = subHandler;
        this.invocationSuitable = invocationSuitable;
    }

    public AbstractVisitorViewer() {
        this(null, false);
    }

    public AbstractVisitorViewer(boolean invocationSuitable) {
        this(null, invocationSuitable);
    }

    /**
     * Object handling method implementation
     * Looks for a method of the current class to process the received parameters
     * If a handler method was found for the current parameters, binds the type of the parameters and the method
     * that handles that parameters so that it doesn't use Reflection API in the future
     * If the method is not found, the base object handler is called
     *
     * @param params - handled parameters
     * @return object type of {@link R}
     */
    @Override
    public R handle(T... params) {
        for (T param : params) {
            if (param == null) {
                return null;
            }
        }
        Class<?> classes[] = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            classes[i] = params[i].getClass();
        }

        String constraintKey = getConstraintKey(params);
        try {
            if (defaultHandles.contains(constraintKey))
                return defaultHandle(params);

            Method method;

            if ((method = methodList.get(constraintKey)) == null) {
                method = getClass().getMethod("handle", classes);
                method.setAccessible(true);
                methodList.put(constraintKey, method);
            }

            return (R) method.invoke(this, params);
        } catch (NoSuchMethodException e) {
            if (invocationSuitable)
                return invokeSuitableMethod(params);
        } catch (IllegalAccessException e) {
            // TODO Add logger
        } catch (InvocationTargetException e) {
            // TODO Add logger
        }
        defaultHandles.add(constraintKey);
        return defaultHandle(params);
    }

    /**
     * Default method to handle object
     * If {@link Handler} sublayer is exist, call handle method at the sub handler
     *
     * @param params - handled params
     * @return {@link R} object with generic type
     */
    protected R defaultHandle(T... params) {
        if (subHandler != null) {
            return (R) subHandler.handle(params);
        }
        return null;
    }

    /**
     * Method to generate constraint key from objects
     *
     * @param objects from which the constraint key is built
     * @return constraint key
     */
    private String getConstraintKey(T... objects) {
        StringBuilder constraintKey = new StringBuilder("");
        for (T object : objects) {
            constraintKey.append(object.getClass().getCanonicalName());
        }
        return constraintKey.toString();
    }

    protected R invokeSuitableMethod(T... params) {
        String constraintKey = getConstraintKey(params);
        try {
            Method method = extractMethod(params);
            method.setAccessible(true);
            methodList.put(constraintKey, method);
            method.invoke(this, params);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
            defaultHandles.add(constraintKey);
        }
        return defaultHandle(params);
    }

    private Method extractMethod(T... params) throws NoSuchMethodException {
        Method[] methods = getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals("handle")
                    && isAssignableByParams(method, params))
                return method;
        }
        throw new NoSuchMethodException();
    }

    private boolean isAssignableByParams(Method method, T[] params) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length == 0 && params.length == 0)
            return true;
        if (parameterTypes.length == 0 && params.length != 0)
            return false;
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i >= params.length)
                return false;
            if (parameterTypes[i] == Object[].class && params[i].getClass() == Object[].class)
                continue;
            if (!parameterTypes[i].isAssignableFrom(params[i].getClass()))
                return false;
        }
        return true;
    }

    protected boolean isInvocationSuitable() {
        return invocationSuitable;
    }
}
