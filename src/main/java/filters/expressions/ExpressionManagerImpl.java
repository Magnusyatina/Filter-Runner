package filters.expressions;

import filters.IOperator;
import filters.Operator;
import filters.converters.*;
import filters.expressions.executors.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class ExpressionManagerImpl implements ExpressionManager {

    private Map<String, ExpressionExecutor> expressionExecutors = new HashMap<>();

    private ValueTypeConverter valueTypeConverter = new ValueTypeStubConverter();

    public ExpressionManagerImpl() {
        expressionExecutors.put(Operator.EQUAL.getOperatorIdentifier(), new ExpressionEqualExecutor(this));
        expressionExecutors.put(Operator.LESS_THAN.getOperatorIdentifier(), new ExpressionLessExecutor(this));
        expressionExecutors.put(Operator.BIGGER_THAN.getOperatorIdentifier(), new ExpressionGreaterExecutor(this));
        expressionExecutors.put(Operator.IN.getOperatorIdentifier(), new ExpressionInExecutor(this));
        valueTypeConverter = new LongConverter(new DoubleConverter(new DateConverter(new SimpleDateFormat("yyyy-HH-dd"))));
    }

    @Override
    public Map<String, ExpressionExecutor> getExpressionsExecutors() {
        return expressionExecutors;
    }

    @Override
    public ExpressionExecutor getExpressionExecutor(IOperator operator) {
        return expressionExecutors.get(operator.getOperatorIdentifier());
    }

    @Override
    public void addExpressionExecutor(IOperator operator, ExpressionExecutor expressionExecutor) {
        expressionExecutors.put(operator.getOperatorIdentifier(), expressionExecutor);
    }

    @Override
    public ValueTypeConverter getValueTypeConverter() {
        return valueTypeConverter;
    }
}
