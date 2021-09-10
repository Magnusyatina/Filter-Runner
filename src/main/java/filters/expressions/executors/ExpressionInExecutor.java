package filters.expressions.executors;

import filters.expressions.ExpressionManager;

import java.util.Collection;

public class ExpressionInExecutor extends ExpressionExecutor {

    public ExpressionInExecutor(ExpressionManager expressionManager) {
        super(expressionManager);
    }

    public boolean handle(Object bean, Collection<?> collection) {
        if (collection == null || collection.isEmpty())
            return false;
        for (Object collectionItem : collection) {
            if (bean.equals(collectionItem))
                return true;
        }
        return false;
    }

    public boolean handle(Object bean, Object[] array) {
        if (array == null || array.length == 0)
            return false;
        for (Object arrayItem : array) {
            if (bean.equals(arrayItem))
                return true;
        }
        return false;
    }

    public boolean handle(Collection<?> beans, Collection<?> collection) {
        if (collection == null || collection.isEmpty())
            return false;
        for(Object bean : beans) {
            if(handle(bean, collection))
                return true;
        }
        return false;
    }

    public boolean handle(Object[] beans, Object[] array) {
        if (array == null || array.length == 0)
            return false;
        for(Object bean : beans) {
            if(handle(bean, array))
                return true;
        }
        return false;
    }
}
