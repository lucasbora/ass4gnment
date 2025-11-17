package utils;

import exceptions.ListException;

import java.util.List;

public interface IList<T> {
    void add(T item);

    T get(int index) throws ListException;

    List<T> getList();
}