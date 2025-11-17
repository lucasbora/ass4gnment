package controller;

import exceptions.DictionaryException;
import exceptions.ExpressionException;
import model.value.IValue;
import model.value.RefValue;
import repository.IRepository;
import utils.IStack;
import exceptions.MyException;
import exceptions.StackException;
import state.PrgState;
import model.statement.IStmt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repo;
    private boolean displayFlag;
    public Controller(IRepository repo) {
        this.repo = repo;
        this.displayFlag = true;
    }
    public Controller(IRepository repo, boolean displayFlag) {
        this.repo = repo;
        this.displayFlag = displayFlag;
    }

    private Map<Integer, IValue> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAddrFromValues(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).getAddr())
                .collect(Collectors.toList());
    }

    public PrgState oneStep(PrgState prg) throws MyException, ExpressionException, DictionaryException {
        IStack<IStmt> stk = prg.getExeStack();
        if (stk.isEmpty()) {
            throw new MyException("Empty execution stack");
        }

        IStmt currentStmt;
        try {
            currentStmt = stk.pop();
        } catch (StackException e) {
            throw new MyException(e.getMessage());
        }
        return currentStmt.execute(prg);
    }

    // CHANGE 2: Add the iterative garbage collection logic
    private List<Integer> getReachableAddresses(Collection<IValue> symTableValues, Map<Integer, IValue> heap) {
        // 1. Start with addresses directly from the root set (Symbol Table)
        Collection<Integer> reachable = new java.util.HashSet<>(getAddrFromValues(symTableValues));

        int sizeBefore = 0;

        // Iterate until no new addresses are found (the set size stops growing)
        while (sizeBefore != reachable.size()) {
            sizeBefore = reachable.size();

            // Collect all IValue objects that are currently known to be reachable
            Collection<IValue> currentReachableValues = reachable.stream()
                    .filter(heap::containsKey) // Ensure the address is still in the current heap
                    .map(heap::get)
                    .collect(Collectors.toList());

            // Find any *new* addresses referenced by these reachable values
            List<Integer> newAddresses = getAddrFromValues(currentReachableValues);

            // Add the new addresses to the set
            reachable.addAll(newAddresses);
        }

        // Return the final list of all reachable addresses
        return new java.util.ArrayList<>(reachable);
    }

    public void allSteps() throws MyException, ExpressionException, DictionaryException {
        PrgState currentPrg = repo.getCurrentPrg();
        repo.logPrgStateExec(); // log initial state

        while (!currentPrg.getExeStack().isEmpty()) {
            oneStep(currentPrg);
            // NEW GARBAGE COLLECTION LOGIC:
            currentPrg.getHeap().setContent(
                    unsafeGarbageCollector(
                            getReachableAddresses( // Use the new method to find all addresses
                                    currentPrg.getSymTable().getContent().values(),
                                    currentPrg.getHeap().getContent()
                            ),
                            currentPrg.getHeap().getContent()
                    )
            );
            repo.logPrgStateExec(); // log after each step
        }
    }


    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }
}