package ua.com.alevel.utils.simplearray.impl;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestWord;
import de.vandermeer.asciithemes.TA_Grid;
import de.vandermeer.asciithemes.TA_GridConfig;
import de.vandermeer.asciithemes.a7.A7_Grids;
import de.vandermeer.asciithemes.u8.U8_Grids;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import dnl.utils.text.table.TextTable;
import ua.com.alevel.db.impl.UserDBImpl;
import ua.com.alevel.entity.User;
import ua.com.alevel.service.impl.UserServiceImpl;
import ua.com.alevel.utils.ConsoleHelperUtil;
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
