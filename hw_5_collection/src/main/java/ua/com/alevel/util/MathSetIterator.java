package ua.com.alevel.util;

import java.util.Iterator;

public class MathSetIterator implements Iterator<Number> {
    private MathSet values;
    private int index = 0;

    public MathSetIterator(MathSet values) {
        this.values = values;
    }

    @Override
    public boolean hasNext() {
        return index < values.size();
    }

    @Override
    public Number next() {
        return values.get(index++);
    }
}
