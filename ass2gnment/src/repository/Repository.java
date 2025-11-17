package repository;

import java.util.*;
import java.io.*;
import state.PrgState;
import exceptions.MyException;
import model.statement.IStmt;
import model.value.IValue;
import model.value.StringValue;

public class Repository implements IRepository {
    private List<PrgState> prgList;
    private String logFilePath;

    public Repository(PrgState prg, String logFilePath) {
        this.prgList = new ArrayList<>();
        this.prgList.add(prg);
        this.logFilePath = logFilePath;
    }

    @Override
    public PrgState getCurrentPrg() {
        return prgList.get(0);
    }

    @Override
    public List<PrgState> getPrgList() {
        return prgList;
    }

    @Override
    public void setPrgList(List<PrgState> prgList) {
        this.prgList = prgList;
    }

    @Override
    public void addPrg(PrgState prg) {
        prgList.add(prg);
    }

    @Override
    public void logPrgStateExec() throws MyException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(
                new FileWriter(logFilePath, true)))) {

            PrgState prg = getCurrentPrg();
            logFile.println("ExeStack:");
            for (IStmt s : prg.getExeStack().toList()) {
                logFile.println("   " + s);
            }

            logFile.println("SymTable:");
            for (Map.Entry<String, IValue> e : prg.getSymTable().getContent().entrySet()) {
                logFile.println("   " + e.getKey() + " --> " + e.getValue());
            }

            logFile.println("Out:");
            for (IValue v : prg.getOutput().getList()) {
                logFile.println("   " + v);
            }
            logFile.println("FileTable:");
            for (Map.Entry<StringValue, BufferedReader> e : prg.getFileTable().getContent().entrySet()) {
                logFile.println("   " + e.getKey());
            }

            logFile.println("Heap:");
            for (Map.Entry<Integer, IValue> e : prg.getHeap().getContent().entrySet()) {
                logFile.println("   " + e.getKey() + " --> " + e.getValue());
            }


            logFile.println("=========================================");

        } catch (IOException e) {
            throw new MyException("Error writing to log file: " + e.getMessage());
        }
    }
}
