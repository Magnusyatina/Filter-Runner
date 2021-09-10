package filters.converters;

import handlers.Handler;

public class LongConverter extends ValueTypeConverter {

    public LongConverter(Handler subHandler) {
        super(subHandler);
    }

    public Object handle(Object value) {
        try {
            return Long.parseLong(String.valueOf(value));
        } catch (NumberFormatException e) {
            return defaultHandle(value);
        }
    }
}
