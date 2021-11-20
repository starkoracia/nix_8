package ua.com.alevel.util;

import java.util.Iterator;

public class MathSet implements SimpleSet<Number> {

    private Number[] values;
    private int size;
    private int capacity = 10;

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

    public MathSet(Number[]... numbers) {
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

    public MathSet(MathSet... numbers) {
        int initCap = getInitCapValue(numbers);
        initCapacity(initCap);
        for (int i = 0; i < numbers.length; i++) {
            MathSet tempMathSet = numbers[i];
            for (int j = 0; j < tempMathSet.size(); j++) {
                add(tempMathSet.get(j));
            }
        }
    }

    private int getInitCapValue(MathSet... numbers) {
        int initCap = 0;
        for (int i = 0; i < numbers.length; i++) {
            initCap += numbers[i].size();
        }
        return initCap;
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

    public boolean add(Number... number) {
        for (int i = 0; i < number.length; i++) {
            add(number[i]);
        }
        return true;
    }

    public void join(MathSet mathSet) {
        for (int i = 0; i < mathSet.size(); i++) {
            add(mathSet.get(i));
        }
    }

    public void join(MathSet... mathSet) {
        for (int i = 0; i < mathSet.length; i++) {
            join(mathSet[i]);
        }
    }

    public void intersection(MathSet mathSet) {
        Number[] tempNumbers = copyOf(values, size);
        clear();
        for (int i = 0; i < tempNumbers.length; i++) {
            Number tempNumber = tempNumbers[i];
            for (int j = 0; j < mathSet.size(); j++) {
                Number number = mathSet.get(j);
                if (tempNumber.equals(number)) {
                    add(number);
                }
            }
        }
    }

    public void intersection(MathSet... mathSet) {
        for (int i = 0; i < mathSet.length; i++) {
            intersection(mathSet[i]);
        }
    }

    public void sortAsc() {
        CollectionUtil.mergeSort(values, 0, size - 1, true);
    }

    public void sortAsc(int firstIndex, int lastIndex) {
        if (firstIndex < 0 || firstIndex >= size || lastIndex < 0 || lastIndex >= size) {
            throw new ArrayIndexOutOfBoundsException(String.format("firstIndex = %d, lastIndex = %d, size = %d", firstIndex, lastIndex, size));
        }
        CollectionUtil.mergeSort(values, firstIndex, lastIndex, true);
    }

    public void sortAsc(Number value) {
        CollectionUtil.mergeSort(values, findIndex(value), size - 1, true);
    }

    public void sortDesc() {
        CollectionUtil.mergeSort(values, 0, size - 1, false);
    }

    public void sortDesc(int firstIndex, int lastIndex) {
        if (firstIndex < 0 || firstIndex >= size || lastIndex < 0 || lastIndex >= size) {
            throw new ArrayIndexOutOfBoundsException(String.format("firstIndex = %d, lastIndex = %d, size = %d", firstIndex, lastIndex, size));
        }
        CollectionUtil.mergeSort(values, firstIndex, lastIndex, false);
    }

    public void sortDesc(Number value) {
        CollectionUtil.mergeSort(values, findIndex(value), size - 1, false);
    }

    private int findIndex(Number number) {
        for (int i = 0; i < size; i++) {
            if (values[i].equals(number)) {
                return i;
            }
        }
        return size - 1;
    }

    public Number getMax() {
        if (size == 0) {
            return null;
        }
        Number maxNumber = values[0];
        for (int i = 1; i < size; i++) {
            if (CollectionUtil.compareNumber(values[i], maxNumber) > 0) {
                maxNumber = values[i];
            }
        }
        return maxNumber;
    }

    public Number getMin() {
        if (size == 0) {
            return null;
        }
        Number minNumber = values[0];
        for (int i = 1; i < size; i++) {
            if (CollectionUtil.compareNumber(values[i], minNumber) < 0) {
                minNumber = values[i];
            }
        }
        return minNumber;
    }

    public MathSet cut(int firstIndex, int lastIndex) {
        MathSet newMathSet = new MathSet();
        for (int i = 0; i < size; i++) {
            if (i < firstIndex || i > lastIndex) {
                newMathSet.add(this.get(i));
            }
        }
        return newMathSet;
    }

    public Number getAverage() {
        if (size == 0) {
            return null;
        }
        Number sum = values[0];
        for (int i = 1; i < size; i++) {
            sum = CollectionUtil.additionNumber(sum, values[i]);
        }
        return CollectionUtil.divideNumber(sum, size);
    }

    public Number getMedian() {
        if (size == 0) {
            return null;
        }
        MathSet sortSet = new MathSet(this);
        sortSet.sortAsc();
        return sortSet.get(size / 2);
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

    public void clear() {
        this.size = 0;
    }

    public void clear(Number[] numbers) {
        Number[] tempNumbers = copyOf(values, size);
        clear();
        for (int i = 0; i < tempNumbers.length; i++) {
            Number tempNumber = tempNumbers[i];
            boolean isNotCompare = true;
            for (int j = 0; j < numbers.length; j++) {
                Number number = numbers[j];
                if (tempNumber.equals(number)) {
                    isNotCompare = false;
                }
            }
            if (isNotCompare) {
                add(tempNumber);
            }
        }
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

    Number[] toArray() {
        return copyOf(values, size);
    }

    Number[] toArray(int firstIndex, int lastIndex) {
        Number[] setNumbers = toArray();
        if (firstIndex < 0 || firstIndex >= size || lastIndex < 0 || lastIndex >= size) {
            throw new ArrayIndexOutOfBoundsException(String.format("firstIndex = %d, lastIndex = %d, size = %d", firstIndex, lastIndex, size));
        }
        int newLength = lastIndex - firstIndex + 1;
        Number[] newNumbers = new Number[newLength];
        System.arraycopy(setNumbers, firstIndex, newNumbers, 0, newLength);
        return newNumbers;
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
