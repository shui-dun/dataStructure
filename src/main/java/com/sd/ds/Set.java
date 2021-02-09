package com.sd.ds;

public interface Set<E> extends DataStructure, Iterable<E> {
    boolean contains(E element);

    void insert(E element);

    void remove(E element);

    void addAll(E... elements);

    void addAll(Set<E> set);

    void removeAll(Set<E> set);

    void retainAll(Set<E> set);

}
