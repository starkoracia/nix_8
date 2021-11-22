package ua.com.alevel.util.interfaces;

public interface SimpleSet<E> extends Iterable<E> {
    boolean add(E e);
    boolean update(int index, E e);
    boolean delete(int index);
    int size();
    E get(int index);
}
