package filters.expressions;

import filters.IOperator;
import filters.converters.ValueTypeConverter;
import filters.expressions.executors.ExpressionExecutor;

import java.util.Map;

public interface ExpressionManager {

    Map<String, ExpressionExecutor> getExpressionsExecutors();

    ExpressionExecutor getExpressionExecutor(IOperator operator);

    void addExpressionExecutor(IOperator operator, ExpressionExecutor expressionExecutor);

    ValueTypeConverter getValueTypeConverter();
}
