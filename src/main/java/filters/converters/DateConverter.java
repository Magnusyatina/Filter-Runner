package filters.converters;

import handlers.Handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter extends ValueTypeConverter {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-HH-dd");

    public DateConverter(SimpleDateFormat simpleDateFormat) {
        this(null, simpleDateFormat);
    }

    public DateConverter(Handler subHandler, SimpleDateFormat simpleDateFormat) {
        super(subHandler);
        this.simpleDateFormat = simpleDateFormat;
    }

    public DateConverter(Handler subHandler) {
        super(subHandler);
    }

    public Object handle(Object value) {
        try {
            return value instanceof Date ? value : simpleDateFormat.parse(String.valueOf(value));
        } catch (ParseException exception) {
            return defaultHandle(value);
        }
    }
}
