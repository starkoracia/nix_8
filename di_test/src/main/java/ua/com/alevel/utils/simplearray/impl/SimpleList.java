package ua.com.alevel.utils.simplearray.impl;

import ua.com.alevel.utils.simplearray.Simple;

import java.util.Arrays;
import java.util.Iterator;


public class SimpleList<E> implements Simple<E> {

    private E[] values;

    public SimpleList() {
        this.values = (E[]) new Object[0];
    }

    @Override
    public boolean add(E e) {
        E[] temp = (E[]) new Object[values.length + 1];
        System.arraycopy(values, 0, temp, 0, values.length);
        values = temp;
        values[values.length - 1] = e;
        return true;
    }

    @Override
    public boolean update(int index, E e) {
        values[index] = e;
        return true;
    }

    @Override
    public boolean delete(int index) {
        E[] temp = (E[]) new Object[values.length - 1];
        System.arraycopy(values, 0, temp, 0, index);
        System.arraycopy(values, index + 1, temp, index, values.length - (index + 1));
        values = temp;
        return true;
    }

    @Override
    public int size() {
        return values.length;
    }

    @Override
    public E get(int index) {
        return values[index];
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator<>(values);
    }

    E[] asArray() {
        return values;
    }

    @Override
    public String toString() {
        return "SimpleList{" +
                "values=" + Arrays.toString(values) +
                '}';
    }
}
