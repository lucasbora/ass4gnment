package model.statement;

import exceptions.ExpressionException;
import exceptions.MyException;
import model.exp.IExp;
import model.type.BoolType;
import model.value.BoolValue;
import model.value.IValue;
import state.PrgState;
import utils.IStack;

public class WhileStmt implements IStmt {
    private final IExp exp;
    private final IStmt stmt;

    public WhileStmt(IExp exp, IStmt stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, ExpressionException {
        IValue condition = exp.eval(state.getSymTable(), state.getHeap());
        if (!(condition.getType() instanceof BoolType))
            throw new MyException("While condition not boolean");

        if (((BoolValue) condition).getVal()) {
            IStack<IStmt> stack = state.getExeStack();
            stack.push(this);
            stack.push(stmt);
        }
        return null;
    }

    @Override
    public IStmt deepCopy() { return new WhileStmt(exp, stmt); }

    @Override
    public String toString() { return "while(" + exp + ") " + stmt; }
}
