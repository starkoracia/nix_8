package ua.com.alevel.utils.simplearray.impl;

import java.util.Iterator;

public class ArrayIterator<E> implements Iterator<E> {
    private E[] values;
    private int index = 0;

    public ArrayIterator(E[] values) {
        this.values = values;
    }

    @Override
    public boolean hasNext() {
        return index < values.length;
    }

    @Override
    public E next() {
        return values[index++];
    }
}
