package filters.expressions.executors;

import filters.expressions.ExpressionManager;

import java.util.Date;

public class ExpressionLessExecutor extends ExpressionExecutor {

    public ExpressionLessExecutor(ExpressionManager expressionManager) {
        super(expressionManager);
    }

    public boolean handle(Date leftValue, Date rightValue) {
        return leftValue.before(rightValue);
    }

    public boolean handle(Integer leftValue, Integer rightValue) {
        return leftValue < rightValue;
    }

    public boolean handle(Double leftValue, Double rightValue) {
        return leftValue < rightValue;
    }

    public boolean handle(Object leftValue, Object rightValue) {
        return false;
    }
}
