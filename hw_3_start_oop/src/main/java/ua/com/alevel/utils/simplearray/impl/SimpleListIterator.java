package ua.com.alevel.utils.simplearray.impl;

import java.util.Iterator;

public class SimpleListIterator<E> implements Iterator<E> {
    private SimpleList<E> values;
    private int index = 0;

    public SimpleListIterator(SimpleList<E> values) {
        this.values = values;
    }

    @Override
    public boolean hasNext() {
        return index < values.size();
    }

    @Override
    public E next() {
        return values.get(index++);
    }
}
