package filters.converters;

public class ValueTypeStubConverter extends ValueTypeConverter {

    public ValueTypeStubConverter() {
        super(null);
    }

    public Object handle(Object value) {
        return value;
    }
}
