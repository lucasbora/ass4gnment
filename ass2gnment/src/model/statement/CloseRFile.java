package model.statement;

import exceptions.DictionaryException;
import exceptions.ExpressionException;
import model.exp.IExp;
import model.type.StringType;
import model.value.*;
import state.PrgState;
import exceptions.MyException;

import java.io.*;

public class CloseRFile implements IStmt {
    private IExp exp;

    public CloseRFile(IExp e) {
        this.exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, ExpressionException, DictionaryException {
        var heap = state.getHeap();
        IValue val = exp.eval(state.getSymTable(), heap);
        if (!(val.getType() instanceof StringType))
            throw new MyException("Expression not string");

        StringValue filename = (StringValue) val;
        BufferedReader br = state.getFileTable().get(filename);

        try {
            br.close();
            state.getFileTable().remove(filename);
        } catch (IOException e) {
            throw new MyException("Error closing file: " + filename.getVal());
        } catch (DictionaryException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp + ")";
    }
}
