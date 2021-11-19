package ua.com.alevel.util;

import java.lang.Number;
import java.util.Arrays;
import java.util.Iterator;

public class MathSet implements SimpleSet<Number> {

    private Number[] values;
    private int size;
    private int capacity = 10;

    public static void main(String[] args) {
        MathSet mathSet = new MathSet();
        System.out.println("mathSet = " + mathSet);
        Number[] numbers = new Number[]{1, 2.0, 3.0f, 1, 1};
        Number[] numbers2 = new Number[]{2, 3.0, 4.0f, 2, 2};

        mathSet = new MathSet(numbers, numbers2);
        System.out.println("mathSet = " + mathSet);

        MathSet mathSet2 = new MathSet(mathSet);
        System.out.println("mathSet2 = " + mathSet2);

        mathSet2.add(0.4);
        mathSet2.add(1.5);
        mathSet2.delete(2);

        MathSet mathSet3 = new MathSet(mathSet2, mathSet );
        System.out.println("mathSet3 = " + mathSet3);
    }

    public MathSet() {
        this.values = new Number[capacity];
    }

    public MathSet(int initCapacity) {
        this.capacity = initCapacity;
        this.values = new Number[capacity];
    }

    public MathSet(Number[] numbers) {
        initCapacity(numbers.length);
        for (int i = 0; i < numbers.length; i++) {
            add(numbers[i]);
        }
    }

    public MathSet(Number[] ... numbers) {
        int initCap = 0;
        for (int i = 0; i < numbers.length; i++) {
            initCap += numbers[i].length;
        }
        initCapacity(initCap);
        for (int i = 0; i < numbers.length; i++) {
            Number[] tempNumbers = numbers[i];
            for (int j = 0; j < tempNumbers.length; j++) {
                add(tempNumbers[j]);
            }
        }
    }

    public MathSet(MathSet numbers) {
        initCapacity(numbers.size());
        for (int i = 0; i < numbers.size(); i++) {
            add(numbers.get(i));
        }
    }

    public MathSet(MathSet ... numbers) {
        int initCap = 0;
        for (int i = 0; i < numbers.length; i++) {
            initCap += numbers[i].size();
        }
        initCapacity(initCap);
        for (int i = 0; i < numbers.length; i++) {
            MathSet tempMathSet = numbers[i];
            for (int j = 0; j < tempMathSet.size(); j++) {
                add(tempMathSet.get(j));
            }
        }
    }

    private void initCapacity(int initCap) {
        initCap = Math.max(10, (initCap + initCap / 2) + 1);
        this.capacity = initCap;
        this.values = new Number[capacity];
    }

    @Override
    public boolean add(Number number) {
        if (isListFull()) {
            listGrow();
        }
        if (notExist(number)) {
            values[size] = number;
            size++;
            return true;
        }
        return false;
    }

    private boolean notExist(Number number) {
        for (int i = 0; i < this.size; i++) {
            if (this.values[i].equals(number)) {
                return false;
            }
        }
        return true;
    }

    private void listGrow() {
        capacity = (capacity + capacity / 2) + 1;
        Number[] temp = new Number[capacity];
        System.arraycopy(values, 0, temp, 0, values.length);
        values = temp;
    }

    private boolean isListFull() {
        return values.length == size;
    }

    @Override
    public boolean update(int index, Number number) {
        if (index >= 0 && index <= size) {
            values[index] = number;
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int index) {
        Number[] temp = new Number[values.length];
        System.arraycopy(values, index + 1, values, index, size - index - 1);
        size--;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Number get(int index) {
        return values[index];
    }

    @Override
    public Iterator<Number> iterator() {
        return new MathSetIterator(this);
    }

    Number[] asArray() {
        return copyOf(values, size);
    }

    @Override
    public String toString() {
        return CollectionUtil.arrayToString(copyOf(values, size));
    }

    private Number[] copyOf(Number[] original, int newLength) {
        Number[] copy = new Number[newLength];
        System.arraycopy(original, 0, copy, 0,
                Math.min(original.length, newLength));
        return copy;
    }
}
