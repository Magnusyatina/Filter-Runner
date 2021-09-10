package filters;

public enum Operator implements IOperator {
    EQUAL,
    LESS_THAN,
    BIGGER_THAN,
    IN;

    @Override
    public String getOperatorIdentifier() {
        return name();
    }

}
