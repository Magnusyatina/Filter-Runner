package filters;

import filters.IOperator;

public class SimpleOperator implements IOperator {

    private String operator;

    @Override
    public String getOperatorIdentifier() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

}
