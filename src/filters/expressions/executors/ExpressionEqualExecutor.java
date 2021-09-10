package filters.expressions.executors;

import filters.expressions.ExpressionManager;

import java.util.Date;
import java.util.Objects;

public class ExpressionEqualExecutor extends ExpressionExecutor {

    public ExpressionEqualExecutor(ExpressionManager expressionManager) {
        super(expressionManager);
    }

    public boolean handle(Object leftValue, Object rightValue) {
        return Objects.equals(leftValue, rightValue);
    }

    public boolean handle(Date leftValue, Date rightValue) {
        return Objects.equals(leftValue, rightValue);
    }

    public boolean Integer(Integer leftValue, Integer rightValue) {
        return Objects.equals(leftValue, rightValue);
    }

    public boolean String(String leftValue, String rightValue) {
        return Objects.equals(leftValue, rightValue);
    }


}
