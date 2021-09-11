package filters.converters;

import handlers.AbstractVisitorViewer;
import handlers.Handler;

public class ValueTypeConverter extends AbstractVisitorViewer {

    public ValueTypeConverter(Handler subHandler) {
        super(subHandler, true);
    }

    @Override
    public Object handle(Object... params) {
        try {
            return super.handle(params);
        } catch (Exception exception) {
            return super.defaultHandle(params);
        }
    }

}
