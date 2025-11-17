package view;

import controller.Controller;
import model.statement.*;
import model.exp.*;
import model.type.*;
import model.value.*;
import repository.*;
import state.PrgState;
import utils.*;

import java.io.BufferedReader;

public class Main3 {
    public static void main(String[] args) {
        // --- Program 1: Simple heap allocation ---
        // Ref int v; new(v, 20); Ref Ref int a; new(a, v); print(v); print(a)
        IStmt ex1 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ConstantValue(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VariableExp("v")),
                                        new CompStmt(
                                                new NewStmt("v",new ConstantValue(new IntValue(30))),
                                                new CompStmt(
                                                        new PrintStmt(new ReadHeapExp(new VariableExp("v"))),
                                                        new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VariableExp("a"))))
                                                )
                                        )

                                )
                        )
                )
        );
// eroare in read heap + garbage collector to check the heap when it deletes
        // --- Program 2: Read heap ---
        // Ref int v; new(v, 20); Ref Ref int a; new(a, v); print(rH(v)); print(rH(rH(a)) + 5)
        IStmt ex2 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ConstantValue(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VariableExp("v")),
                                        new CompStmt(
                                                new PrintStmt(new ReadHeapExp(new VariableExp("v"))),
                                                new PrintStmt(
                                                        new ArithExp('+',
                                                                new ReadHeapExp(
                                                                        new ReadHeapExp(new VariableExp("a"))
                                                                ),
                                                                new ConstantValue(new IntValue(5))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        // --- Program 3: Write heap ---
        // Ref int v; new(v, 20); print(rH(v)); wH(v, 30); print(rH(v) + 5)
        IStmt ex3 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ConstantValue(new IntValue(20))),
                        new CompStmt(
                                new PrintStmt(new ReadHeapExp(new VariableExp("v"))),
                                new CompStmt(
                                        new WriteHeapStmt("v", new ConstantValue(new IntValue(30))),
                                        new PrintStmt(
                                                new ArithExp('+',
                                                        new ReadHeapExp(new VariableExp("v")),
                                                        new ConstantValue(new IntValue(5))
                                                )
                                        )
                                )
                        )
                )
        );

        // --- Program 4: While loop ---
        // int v; v = 4; while (v > 0) { print(v); v = v - 1; } print(v)
        IStmt ex4 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ConstantValue(new IntValue(4))),
                        new CompStmt(
                                new WhileStmt(
                                        new RelationalExp(">", new VariableExp("v"), new ConstantValue(new IntValue(0))),
                                        new CompStmt(
                                                new PrintStmt(new VariableExp("v")),
                                                new AssignStmt("v",
                                                        new ArithExp('-',
                                                                new VariableExp("v"),
                                                                new ConstantValue(new IntValue(1))
                                                        )
                                                )
                                        )
                                ),
                                new PrintStmt(new VariableExp("v"))
                        )
                )
        );

        // --- Build and link interpreters for each program ---
        createAndAddProgram("1", ex1, "log1.txt", "Heap allocation example");
        createAndAddProgram("2", ex2, "log2.txt", "Heap read example");
        createAndAddProgram("3", ex3, "log3.txt", "Heap write example");
        createAndAddProgram("4", ex4, "log4.txt", "While loop example");

        // Show menu
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), new Controller(new Repository(buildProgram(ex1), "log1.txt"))));
        menu.addCommand(new RunExample("2", ex2.toString(), new Controller(new Repository(buildProgram(ex2), "log2.txt"))));
        menu.addCommand(new RunExample("3", ex3.toString(), new Controller(new Repository(buildProgram(ex3), "log3.txt"))));
        menu.addCommand(new RunExample("4", ex4.toString(), new Controller(new Repository(buildProgram(ex4), "log4.txt"))));
        menu.show();
    }

    private static PrgState buildProgram(IStmt stmt) {
        IStack<IStmt> exeStack = new MyStack<>();
        IDict<String, IValue> symTable = new MyDict<>();
        IList<IValue> output = new MyList<>();
        IDict<StringValue, BufferedReader> fileTable = new MyDict<>();
        IHeap<Integer, IValue> heap = new MyHeap<>();

        return new PrgState(exeStack, symTable, output, fileTable, heap, stmt);
    }

    private static void createAndAddProgram(String key, IStmt ex, String logFile, String desc) {
        System.out.printf("%s : %s%n", key, desc);
    }
}
