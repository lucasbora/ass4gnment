package model.exp;

import exceptions.ExpressionException;
import model.value.*;
import model.type.*;
import exceptions.MyException;
import utils.IDict;
import utils.IHeap;

public class RelationalExp implements IExp {
    private IExp e1, e2;
    private String op;

    public RelationalExp(String op, IExp e1, IExp e2) {
        this.op = op;
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public IValue eval(IDict<String, IValue> tbl, IHeap<Integer, IValue> heap) throws MyException, ExpressionException {
        IValue v1 = e1.eval(tbl, heap);
        if (!(v1.getType() instanceof IntType))
            throw new MyException("First operand not int");

        IValue v2 = e2.eval(tbl, heap);
        if (!(v2.getType() instanceof IntType))
            throw new MyException("Second operand not int");

        int n1 = ((IntValue) v1).getVal();
        int n2 = ((IntValue) v2).getVal();

        return switch (op) {
            case "<" -> new BoolValue(n1 < n2);
            case "<=" -> new BoolValue(n1 <= n2);
            case "==" -> new BoolValue(n1 == n2);
            case "!=" -> new BoolValue(n1 != n2);
            case ">" -> new BoolValue(n1 > n2);
            case ">=" -> new BoolValue(n1 >= n2);
            default -> throw new MyException("Invalid relational operator");
        };
    }

    @Override
    public IExp deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return e1 + " " + op + " " + e2;
    }
}
