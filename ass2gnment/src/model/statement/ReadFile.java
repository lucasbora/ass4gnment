package model.statement;

import exceptions.DictionaryException;
import exceptions.ExpressionException;
import model.exp.IExp;
import model.type.*;
import model.value.*;
import state.PrgState;
import exceptions.MyException;

import java.io.*;

public class ReadFile implements IStmt {
    private IExp exp;
    private String varName;

    public ReadFile(IExp e, String varName) {
        this.exp = e;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, DictionaryException, ExpressionException {
        var heap = state.getHeap();
        if (!state.getSymTable().isDefined(varName))
            throw new MyException("Variable not defined: " + varName);

        IValue varVal = state.getSymTable().get(varName);
        if (!(varVal.getType() instanceof IntType))
            throw new MyException("Variable must be int");

        IValue val = exp.eval(state.getSymTable(), heap);
        if (!(val.getType() instanceof StringType))
            throw new MyException("Expression must be string");
        //check if its open
        StringValue filename = (StringValue) val;
        BufferedReader br = state.getFileTable().get(filename);

        try {
            String line = br.readLine();
            int intVal = (line == null) ? 0 : Integer.parseInt(line);
            state.getSymTable().update(varName, new IntValue(intVal));
        } catch (IOException e) {
            throw new MyException("Error reading from file: " + filename.getVal());
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "readFile(" + exp + ", " + varName + ")";
    }
}
