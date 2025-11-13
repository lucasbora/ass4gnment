package model.statement;

import exceptions.DictionaryException;
import exceptions.ExpressionException;
import exceptions.MyException;
import model.exp.IExp;
import model.type.RefType;
import model.value.RefValue;
import model.value.IValue;
import state.PrgState;
import utils.IHeap;
import utils.IDict;

public class WriteHeapStmt implements IStmt {
    private final String varName;
    private final IExp exp;

    public WriteHeapStmt(String varName, IExp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, DictionaryException, ExpressionException {
        IDict<String, IValue> symTable = state.getSymTable();
        IHeap<Integer, IValue> heap = state.getHeap();

        if (!symTable.isDefined(varName))
            throw new MyException("Variable not defined: " + varName);

        IValue varValue = symTable.get(varName);
        if (!(varValue instanceof RefValue))
            throw new MyException("Variable not RefValue: " + varName);

        RefValue refVal = (RefValue) varValue;
        if (!heap.isDefined(refVal.getAddr()))
            throw new MyException("Address not in heap: " + refVal.getAddr());

        IValue newVal = exp.eval(symTable, heap);
        if (!newVal.getType().equals(refVal.getLocationType()))
            throw new MyException("Type mismatch in writeHeap for " + varName);

        heap.update(refVal.getAddr(), newVal);
        return null;
    }

    @Override
    public IStmt deepCopy() { return new WriteHeapStmt(varName, exp); }

    @Override
    public String toString() { return "wH(" + varName + ", " + exp + ")"; }
}
