package filters.expressions.executors;

import filters.converters.ValueTypeConverter;
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
        ValueTypeConverter valueTypeConverter = expressionManager.getValueTypeConverter();
        leftValue = valueTypeConverter.handle(leftValue);
        rightValue = valueTypeConverter.handle(rightValue);
        if (leftValue != null && rightValue != null)
            return handle(leftValue, rightValue);
        return false;
    }

}
