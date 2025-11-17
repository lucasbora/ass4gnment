package utils;

import exceptions.MyException;
import java.util.Map;

public interface IHeap<K, V> {
    void put(K key, V value);
    V get(K key) throws MyException;
    boolean isDefined(K key);
    void update(K key, V value) throws MyException;
    Map<K, V> getContent();
    void setContent(Map<K, V> newContent);
    int allocate(V value); // returns the new address
}
