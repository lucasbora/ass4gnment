package model.value;

import model.type.StringType;
import model.type.IType;

public class StringValue implements IValue {
    private final String val;

    public StringValue(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public IValue deepCopy() {
        return null;
    }

    @Override
    public boolean equals(IValue other) {
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof StringValue &&
                ((StringValue) other).getVal().equals(this.val);
    }

    @Override
    public String toString() {
        return val;
    }
}
