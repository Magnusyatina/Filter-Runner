package filters.expressions.executors;

import filters.converters.ValueTypeConverter;
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

    public boolean handle(Object leftValue, Object rightValue) {
        ValueTypeConverter valueTypeConverter = expressionManager.getValueTypeConverter();
        leftValue = valueTypeConverter.handle(leftValue);
        rightValue = valueTypeConverter.handle(rightValue);
        if (leftValue != null && rightValue != null)
            return super.handle(leftValue, rightValue);
        return false;
    }
}
