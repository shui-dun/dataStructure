package com.sd.ds;

public interface SortedSet<E> extends Set<E> {
    E first();

    E last();

    E floor(E element);

    E ceiling(E element);

    E lower(E element);

    E higher(E element);

    Iterable<E> subSet(E begin, E end);
}
