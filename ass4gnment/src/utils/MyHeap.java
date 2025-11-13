package utils;

import exceptions.MyException;
import java.util.HashMap;
import java.util.Map;

public class MyHeap<K, V> implements IHeap<K, V> {
    private Map<Integer, V> heap;
    private int nextFree;

    public MyHeap() {
        heap = new HashMap<>();
        nextFree = 1; // start addresses from 1
    }

    @Override
    public void put(K key, V value) {
        heap.put((Integer) key, value);
    }

    @Override
    public V get(K key) throws MyException {
        if (!heap.containsKey(key))
            throw new MyException("Invalid heap address: " + key);
        return heap.get(key);
    }

    @Override
    public boolean isDefined(K key) {
        return heap.containsKey(key);
    }

    @Override
    public void update(K key, V value) throws MyException {
        if (!heap.containsKey(key))
            throw new MyException("Heap address not defined: " + key);
        heap.put((Integer) key, value);
    }

    @Override
    public Map<K, V> getContent() {
        return (Map<K, V>) heap;
    }

    @Override
    public void setContent(Map<K, V> newContent) {
        heap = (Map<Integer, V>) newContent;
    }

    @Override
    public int allocate(V value) {
        heap.put(nextFree, value);
        return nextFree++;
    }
}










