package filters.expressions;

import filters.IOperator;

import java.util.LinkedList;
import java.util.List;

public class ExpressionBuilder {

    private List<Expression> expressions = new LinkedList<>();

    private Expression currentExpression;

    public static ExpressionBuilder getInstance() {
        ExpressionBuilder expressionBuilder = new ExpressionBuilder();
        expressionBuilder.currentExpression = new Expression();
        return expressionBuilder;
    }

    public ExpressionBuilder fieldPath(String fieldPath) {
        currentExpression.setFieldPath(fieldPath);
        return this;
    }

    public ExpressionBuilder operator(IOperator operator) {
        currentExpression.setOperator(operator);
        return this;
    }

    public ExpressionBuilder value(Object value) {
        currentExpression.setValue(value);
        return this;
    }

    public ExpressionBuilder next() {
        expressions.add(currentExpression);
        currentExpression = new Expression();
        return this;
    }

    public List<Expression> build() {
        expressions.add(currentExpression);
        return expressions;
    }

    public ExpressionBuilder clear() {
        expressions = new LinkedList<>();
        currentExpression = new Expression();
        return this;
    }
}
