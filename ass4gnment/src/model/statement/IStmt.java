package model.statement;

import exceptions.DictionaryException;
import exceptions.ExpressionException;
import exceptions.MyException;
import state.PrgState;

public interface IStmt {
    PrgState execute(PrgState prg) throws MyException, ExpressionException, DictionaryException;

    IStmt deepCopy();
}