package utils;

import java.util.Map;

import exceptions.DictionaryException;

import java.util.HashMap;

public class MyDict<K, V> implements IDict<K, V> {
    private Map<K, V> dict;

    public MyDict() {
        dict = new HashMap<K, V>();
    }

    @Override
    public void put(K key, V value) {
        dict.put(key, value);
    }

    @Override
    public Map<K, V> getContent() {
        return dict;
    }

    @Override
    public void remove(K key) throws DictionaryException {
        if (!dict.containsKey(key))
            throw new DictionaryException("Cannot remove non-existent key: " + key);
        dict.remove(key);
    }

    @Override
    public V get(K key) throws DictionaryException {
        if (!isDefined(key)) {
            throw new DictionaryException("Key not found in dictionary: " + key);
        }
        return dict.get(key);
    }

    @Override
    public boolean isDefined(K key) {
        return dict.containsKey(key);
    }

    @Override
    public String toString() {
        return dict.toString();
    }

    @Override
    public void update(K key, V value) throws DictionaryException {
        if (!isDefined(key)) {
            throw new DictionaryException("Key not found in dictionary: " + key);
        }
        dict.put(key, value);
    }

}