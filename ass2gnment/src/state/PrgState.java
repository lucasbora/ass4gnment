package state;

import model.value.StringValue;
import utils.IHeap;
import utils.IStack;
import utils.IDict;
import utils.IList;
import model.statement.IStmt;
import model.value.IValue;

import java.io.BufferedReader;
import java.util.List;

public class PrgState {
    private IStack<IStmt> exeStack;

    public IStack<IStmt> getExeStack() {
        return exeStack;
    }

    private IDict<String, IValue> symTable;

    public IDict<String, IValue> getSymTable() {
        return symTable;
    }

    private IList<IValue> output;

    public IList<IValue> getOutput() {
        return output;
    }

    private IStmt originalProgram;

    public PrgState(IStack<IStmt> exeStack, IDict<String, IValue> symTable, IList<IValue> output, IStmt originalProgram) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.output = output;
        this.originalProgram = originalProgram.deepCopy();

        exeStack.push(originalProgram);
    }


    private IDict<StringValue, BufferedReader> fileTable;
    private IHeap<Integer, IValue> heap;
    public PrgState(IStack<IStmt> stk, IDict<String, IValue> symtbl,
                    IList<IValue> out, IDict<StringValue, BufferedReader> fileTable,
                    IHeap<Integer, IValue> heap,
                    IStmt prg) {
        this.exeStack = stk;
        this.symTable = symtbl;
        this.output = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.originalProgram = prg;
        stk.push(prg);
    }

    public IHeap<Integer, IValue> getHeap() { return heap; }

    public IDict<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }



    @Override
    public String toString() {
        return "PrgState{\n" + "exeStack=" + exeStack.getList() + ",\n symTable=" + symTable + ",\n output=" + output
                + ",\n originalProgram="
                + originalProgram + "\n}";
    }
}