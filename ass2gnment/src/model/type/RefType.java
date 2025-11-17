package model.type;

import model.value.IValue;
import model.value.RefValue;

public class RefType implements IType {
    private final IType inner;

    public RefType(IType inner) {
        this.inner = inner;
    }

    public IType getInner() {
        return inner;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof RefType && inner.equals(((RefType) other).getInner());
    }

    @Override
    public IType deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }

    @Override
    public IValue defaultValue() {
        return new RefValue(0, inner); // address 0 = null reference
    }
}
