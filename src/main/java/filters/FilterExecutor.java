package filters;

import filters.errors.NotSupportedFieldTypeException;
import filters.expressions.Expression;
import filters.expressions.ExpressionManager;
import filters.expressions.executors.ExpressionExecutor;
import handlers.AbstractVisitorViewer;
import utils.beans.extractors.ValueExtractor;

import java.util.*;

public class FilterExecutor extends AbstractVisitorViewer<Boolean, Object> {

    private final ValueExtractor valueExtractor;

    private final ExpressionManager expressionManager;

    private final List<Object> beans;

    private List<Expression> expressions;

    private boolean recursiveFilter = false;

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

    public List<Object> filter(List<Expression> expressions) {
        this.expressions = expressions;
        List<Object> filteredBeans = new LinkedList<>();
        for (Object bean : beans) {
            if (isValid(bean, expressions))
                filteredBeans.add(bean);
        }
        return filteredBeans;
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
        valueExtractor.getBeanAnalyzer().registerMethod(bean.getClass(), fieldName);
        bean = valueExtractor.getValue(bean, fieldName);
        return handle(bean, path, expression);
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
