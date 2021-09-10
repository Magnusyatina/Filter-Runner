package filters.expressions.executors;

import filters.expressions.ExpressionManager;
import handlers.AbstractVisitorViewer;

public class ExpressionExecutor extends AbstractVisitorViewer<Boolean, Object> {

    protected ExpressionManager expressionManager;

    public ExpressionExecutor(ExpressionManager expressionManager) {
        super(null, true);
        this.expressionManager = expressionManager;
    }

    @Override
    protected Boolean defaultHandle(Object[] params) {
        return false;
    }
}
