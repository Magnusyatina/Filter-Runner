package filters.converters;

import handlers.Handler;

public class DoubleConverter extends ValueTypeConverter {

    public DoubleConverter(Handler subHandler) {
        super(subHandler);
    }

    public Object handle(Object value) {
        try {
            return Double.parseDouble(String.valueOf(value));
        } catch (NumberFormatException e) {
            return defaultHandle(value);
        }
    }
}
