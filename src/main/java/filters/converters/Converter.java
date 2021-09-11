package filters.converters;

import filters.annotations.Represent;

public abstract class Converter <R, T> {

    public abstract R convert(Represent represent, T object);
}
