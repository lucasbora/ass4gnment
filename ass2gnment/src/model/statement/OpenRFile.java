package model.statement;

import exceptions.ExpressionException;
import model.exp.IExp;
import model.type.StringType;
import model.value.*;
import state.PrgState;
import exceptions.MyException;

import java.io.*;

public class OpenRFile implements IStmt {
    private IExp exp;

    public OpenRFile(IExp e) {
        this.exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, ExpressionException {
        var heap = state.getHeap();
        IValue val = exp.eval(state.getSymTable(), heap);
        if (!(val.getType() instanceof StringType))
            throw new MyException("OpenRFile: expression not string type");

        StringValue filename = (StringValue) val;
        if (state.getFileTable().isDefined(filename))
            throw new MyException("File already open: " + filename.getVal());

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename.getVal()));
            state.getFileTable().put(filename, br);
        } catch (IOException e) {
            throw new MyException("Cannot open file: " + filename.getVal());
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "openRFile(" + exp + ")";
    }
}
