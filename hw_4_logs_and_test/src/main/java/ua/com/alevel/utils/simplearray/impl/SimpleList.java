package ua.com.alevel.utils.simplearray.impl;

import ua.com.alevel.utils.simplearray.Simple;

import java.util.Arrays;
import java.util.Iterator;

public class SimpleList<E> implements Simple<E> {

    private E[] values;
    private int size;
    private int capacity = 10;

    public SimpleList() {
        this.values = (E[]) new Object[capacity];
    }

    public SimpleList(int initCapacity) {
        this.capacity = initCapacity;
        this.values = (E[]) new Object[capacity];
    }

    @Override
    public boolean add(E e) {
        if (isListFull()) {
            listGrow();
        }
        values[size] = e;
        size++;
        return true;
    }

    private void listGrow() {
        capacity = (capacity + capacity / 2) + 1;
        E[] temp = (E[]) new Object[capacity];
        System.arraycopy(values, 0, temp, 0, values.length);
        values = temp;
    }

    private boolean isListFull() {
        return values.length == size;
    }

    @Override
    public boolean update(int index, E e) {
        if(index >= 0 && index <= size) {
            values[index] = e;
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int index) {
        E[] temp = (E[]) new Object[values.length];
        System.arraycopy(values, index + 1, values, index, size - index - 1);
        size--;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E get(int index) {
        return values[index];
    }

    @Override
    public Iterator<E> iterator() {
        return new SimpleListIterator<E>(this);
    }

    E[] asArray() {
        return Arrays.copyOf(values, size);
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(values, size));
    }
}
