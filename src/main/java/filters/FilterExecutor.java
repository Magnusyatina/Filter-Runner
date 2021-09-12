package filters;

import filters.annotations.Represent;
import filters.converters.Converter;
import filters.errors.NotSupportedFieldTypeException;
import filters.expressions.Expression;
import filters.expressions.ExpressionManager;
import filters.expressions.executors.ExpressionExecutor;
import handlers.AbstractVisitorViewer;
import utils.beans.extractors.ValueExtractor;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.*;

public class FilterExecutor extends AbstractVisitorViewer<Boolean, Object> {

    private final ValueExtractor valueExtractor;

    private final ExpressionManager expressionManager;

    private final List<Object> beans;

    private List<Expression> expressions;

    private boolean recursiveFilter = false;

    private Map<Class<?>, Map<String, Represent>> represents = new HashMap<>();

    public FilterExecutor(ValueExtractor valueExtractor, ExpressionManager expressionManager, List<Object> beans, boolean recursiveFilter) {
        super(null, true);
        this.valueExtractor = valueExtractor;
        this.expressionManager = expressionManager;
        this.beans = beans;
        this.recursiveFilter = recursiveFilter;
    }

    public FilterExecutor(ValueExtractor valueExtractor, ExpressionManager expressionManager, List<Object> beans) {
        this(valueExtractor, expressionManager, beans, false);
    }

    public List<Object> filter(final List<Expression> expressions) {
        this.expressions = expressions;
        registerMethods(expressions);
        final List<Object> filteredBeans = new LinkedList<>();
        List<Future> submits = new LinkedList<>();
        for (final Object bean : beans) {
            if (isValid(bean, expressions))
                filteredBeans.add(bean);
        }
        return filteredBeans;
    }

    private void registerMethods(List<Expression> expressions) {
        Class beanClass = beans.get(0).getClass();
        for(Expression expression : expressions) {
            valueExtractor.getBeanAnalyzer().registerMethod(beanClass, expression.getFieldPath());
        }
    }

    public boolean isValid(Object bean, List<Expression> expressions) {
        for (Expression expression : expressions) {
            if (!isValid(bean, expression))
                return false;
        }
        return true;
    }

    public boolean isValid(Object bean, Expression expression) {
        LinkedList<String> fieldNames = new LinkedList<>(Arrays.asList(expression.getFieldPath().split("\\.")));
        return isValid(bean, fieldNames.removeFirst(), fieldNames, expression);
    }

    public boolean isValid(Object bean, String fieldName, LinkedList<String> path, Expression expression) {
        Represent annotation = findAnnotation(bean, fieldName);
        if(annotation != null) {
            try {
                Converter converter = annotation.using().newInstance();
                bean = valueExtractor.getValue(bean, fieldName, annotation);
                if(bean.getClass() != expression.getValue().getClass())
                    expression.setValue(converter.convert(annotation, expression.getValue()));
            } catch (InstantiationException | IllegalAccessException e) {
                return false;
            }
        }
        else bean = valueExtractor.getValue(bean, fieldName);
        return handle(bean, path, expression);
    }

    public Represent findAnnotation(Object bean, String fieldName) {
        Class<?> beanClass = bean.getClass();
        Map<String, Represent> fieldRepresents = represents.get(beanClass);
        if(fieldRepresents == null)
            return findAnnotation(beanClass, fieldName);
        Represent represent = fieldRepresents.get(fieldName);
        if(represent == null)
            return findAnnotation(beanClass, fieldName);
        return represent;
    }

    public Represent findAnnotation(Class<?> beanClass, String fieldName) {
        try {
            Field declaredField = beanClass.getDeclaredField(fieldName);
            if(!declaredField.isAnnotationPresent(Represent.class))
                return null;
            Represent annotation = declaredField.getAnnotation(Represent.class);
            Map<String, Represent> annotations = represents.get(beanClass);
            if(annotations == null) {
                annotations = new HashMap<>();
                represents.put(beanClass, annotations);
            }
            annotations.put(fieldName, annotation);
            return annotation;
        } catch (NoSuchFieldException e) {
        }
        return null;
    }

    public Boolean handle(Collection collection, Expression expression) {
        throw new NotSupportedFieldTypeException();
    }

    public Boolean handle(Map map, Expression expression) {
        throw new NotSupportedFieldTypeException();
    }

    public Boolean handle(Object bean, LinkedList<String> path, Expression expression) {
        if(!path.isEmpty())
            return isValid(bean, path.removeFirst(), path, expression);
        ExpressionExecutor expressionExecutor = expressionManager.getExpressionExecutor(expression.getOperator());
        if (expressionExecutor == null)
            return false;
        return expressionExecutor.handle(bean, expression.getValue());
    }
}
