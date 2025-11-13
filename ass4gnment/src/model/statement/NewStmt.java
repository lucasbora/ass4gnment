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

public class NewStmt implements IStmt {
    private final String varName;
    private final IExp exp;

    public NewStmt(String varName, IExp exp) {
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
        if (!(varValue.getType() instanceof RefType))
            throw new MyException("Variable not RefType: " + varName);

        IValue evaluated = exp.eval(symTable, heap);
        RefType refType = (RefType) varValue.getType();
        if (!evaluated.getType().equals(refType.getInner()))
            throw new MyException("Type mismatch in new(" + varName + ")");

        int addr = heap.allocate(evaluated);
        symTable.update(varName, new RefValue(addr, refType.getInner()));

        return null;
    }

    @Override
    public IStmt deepCopy() { return new NewStmt(varName, exp); }

    @Override
    public String toString() { return "new(" + varName + ", " + exp + ")"; }
}
