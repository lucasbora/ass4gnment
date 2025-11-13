package repository;

import exceptions.MyException;
import state.PrgState;
import java.util.List;

public interface IRepository {
    PrgState getCurrentPrg();
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prgList);
    void addPrg(PrgState prg);
    void logPrgStateExec() throws MyException;
}
