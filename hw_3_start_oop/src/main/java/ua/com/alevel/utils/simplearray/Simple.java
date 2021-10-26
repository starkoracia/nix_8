package ua.com.alevel.utils.simplearray;

public interface Simple<E> extends Iterable<E> {
    boolean add(E e);
    boolean update(int index, E e);
    boolean delete(int index);
    int size();
    E get(int index);
}
