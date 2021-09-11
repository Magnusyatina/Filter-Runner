package filters.expressions.executors;

import filters.expressions.ExpressionManager;

import java.util.Date;

public class ExpressionGreaterExecutor extends ExpressionExecutor {

    public ExpressionGreaterExecutor(ExpressionManager expressionManager) {
        super(expressionManager);
    }

    public boolean handle(Date leftValue, Date rightValue) {
        return leftValue.after(rightValue);
    }

    public boolean handle(Integer leftValue, Integer rightValue) {
        return leftValue > rightValue;
    }

    public boolean handle(Double leftValue, Double rightValue) {
        return leftValue > rightValue;
    }

    public boolean handle(Object leftValue, Object rightValue) {
        return false;
    }

}
