package filters.converters;

import handlers.AbstractVisitorViewer;
import handlers.Handler;

import java.util.HashSet;
import java.util.Set;

public class ValueTypeConverter extends AbstractVisitorViewer {

    public ValueTypeConverter(Handler subHandler) {
        super(subHandler, true);
    }

    @Override
    protected Object defaultHandle(Object... params) {
        String constraintKey = getConstraintKey(params);
        if(!defaultHandles.contains(constraintKey))
            defaultHandles.add(constraintKey);
        return super.defaultHandle(params);
    }
}
