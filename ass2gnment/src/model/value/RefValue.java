package model.value;

import model.type.RefType;
import model.type.IType;

public class RefValue implements IValue {
    private final int address;
    private final IType locationType;

    public RefValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddr() {
        return address;
    }

    public IType getLocationType() {
        return locationType;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
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
    public String toString() {
        return "(" + address + ", " + locationType + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof RefValue)
            return address == ((RefValue) other).address &&
                    locationType.equals(((RefValue) other).locationType);
        return false;
    }
}
