package filters;

import filters.errors.NotSupportedFieldTypeException;
import filters.expressions.Expression;
import filters.expressions.ExpressionManager;
import filters.expressions.executors.ExpressionExecutor;
import handlers.AbstractVisitorViewer;
import utils.beans.extractors.ValueExtractor;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FilterExecutor extends AbstractVisitorViewer {

    private final ValueExtractor valueExtractor;

    private final ExpressionManager expressionManager;

    private final List<Object> beans;

    private List<Expression> expressions;


    public FilterExecutor(ValueExtractor valueExtractor, ExpressionManager expressionManager, List<Object> beans) {
        super(null, true);
        this.valueExtractor = valueExtractor;
        this.expressionManager = expressionManager;
        this.beans = beans;
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
        String[] fieldNames = expression.getFieldPath().split("\\.");
        String fieldPath = "";
        Object root = bean;
        for (String fieldName : fieldNames) {
            fieldPath += "\\." + fieldName;
            root = valueExtractor.getValue(root, fieldName);
            root = handle(root, expression);
        }
        ExpressionExecutor expressionExecutor = expressionManager.getExpressionExecutor(expression.getOperator());
        if (expressionExecutor == null)
            return false;
        return expressionExecutor.handle(root, expression.getValue());
    }

    public void handle(Collection collection, Expression expression) {
        throw new NotSupportedFieldTypeException();
    }

    public void handle(Map map, Expression expression) {
        throw new NotSupportedFieldTypeException();
    }

    public Object handle(Object bean, Expression expression) {
        return bean;
    }
}
