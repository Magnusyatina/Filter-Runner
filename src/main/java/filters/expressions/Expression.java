package filters.expressions;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import filters.IOperator;
import filters.SimpleOperator;

public class Expression {

    private String fieldPath;

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = SimpleOperator.class)
    private IOperator operator;

    private Object value;

    private boolean required = false;

    public Expression() {
    }

    public Expression(String fieldPath, IOperator operator, Object value) {
        this(fieldPath, operator, value, false);
    }

    public Expression(String fieldPath, IOperator operator, Object value, boolean required) {
        this.fieldPath = fieldPath;
        this.operator = operator;
        this.value = value;
        this.required = required;
    }

    public String getFieldPath() {
        return fieldPath;
    }

    public void setFieldPath(String fieldPath) {
        this.fieldPath = fieldPath;
    }

    public IOperator getOperator() {
        return operator;
    }

    public void setOperator(IOperator operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
