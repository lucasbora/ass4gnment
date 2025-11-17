package utils;

import exceptions.DictionaryException;

import java.util.Map;

public interface IDict<K, V> {
    void put(K key, V value);

    V get(K key) throws DictionaryException;

    boolean isDefined(K key);

    void update(K key, V value) throws DictionaryException;

    Map<K, V> getContent();

    void remove(K key) throws DictionaryException;

}