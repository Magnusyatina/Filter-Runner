package filters.converters;

import handlers.AbstractVisitorViewer;
import handlers.Handler;

public class ValueTypeConverter extends AbstractVisitorViewer {

    public ValueTypeConverter(Handler subHandler) {
        super(subHandler, true);
    }
}
