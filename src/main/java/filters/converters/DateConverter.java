package filters.converters;

import filters.annotations.Represent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter extends Converter<Date, Object> {

    @Override
    public Date convert(Represent represent, Object object) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(represent.pattern());
        try {
            return simpleDateFormat.parse(String.valueOf(object));
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
