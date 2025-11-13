package model.exp;

import exceptions.ExpressionException;
import exceptions.MyException;
import model.value.RefValue;
import model.value.IValue;
import utils.IDict;
import utils.IHeap;

public class ReadHeapExp implements IExp {
    private final IExp exp;

    public ReadHeapExp(IExp exp) {
        this.exp = exp;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<Integer, IValue> heap) throws MyException, ExpressionException {
        IValue value = exp.eval(symTable, heap);
        if (!(value instanceof RefValue))
            throw new MyException("ReadHeap expects RefValue");

        int addr = ((RefValue) value).getAddr();
        if (!heap.isDefined(addr))
            throw new MyException("Invalid heap address: " + addr);

        return heap.get(addr);
    }

    @Override
    public IExp deepCopy() {
        return null;
    }

    @Override
    public String toString() { return "rH(" + exp + ")"; }
}
