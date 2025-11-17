package model.type;

import model.value.*;

public class StringType implements IType {
    @Override
    public boolean equals(Object other) {
        return other instanceof StringType;
    }

    @Override
    public IType deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public IValue defaultValue() {
        return new StringValue("");
    }
}
