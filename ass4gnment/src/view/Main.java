//package view;
//
//import controller.Controller;
//import model.statement.*;
//import model.type.*;
//import model.value.*;
//import model.exp.*;
//import repository.*;
//import state.PrgState;
//import utils.*;
//
//import java.io.BufferedReader;
//
//public class Main {
//    public static void main(String[] args) {
//        // Example for file handling
//        IStmt ex1 = new CompStmt(
//                new VarDeclStmt("varf", new StringType()),
//                new CompStmt(
//                        new AssignStmt("varf", new ConstantValue(new StringValue("test.in"))),
//                        new CompStmt(
//                                new OpenRFile(new VariableExp("varf")),
//                                new CompStmt(
//                                        new VarDeclStmt("varc", new IntType()),
//                                        new CompStmt(
//                                                new ReadFile(new VariableExp("varf"), "varc"),
//                                                new CompStmt(
//                                                        new PrintStmt(new VariableExp("varc")),
//                                                        new CompStmt(
//                                                                new ReadFile(new VariableExp("varf"), "varc"),
//                                                                new CompStmt(
//                                                                        new PrintStmt(new VariableExp("varc")),
//                                                                        new CloseRFile(new VariableExp("varf"))
//                                                                )
//                                                        )
//                                                )
//                                        )
//                                )
//                        )
//                )
//        );
//
//        PrgState prg1 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new MyDict<StringValue, BufferedReader>(),new MyHeap<>(), ex1);
//        IRepository repo1 = new Repository(prg1, "log1.txt");
//        Controller ctrl1 = new Controller(repo1);
//
//        TextMenu menu = new TextMenu();
//        menu.addCommand(new ExitCommand("0", "exit"));
//        menu.addCommand(new RunExample("1", ex1.toString(), ctrl1));
//        menu.show();
//    }
//}
