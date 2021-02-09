package com.sd.ds;

public abstract class AbstractList<E> implements List<E> {
    @Override
    public void add(E x) {
        add(size(), x);
    }

    @Override
    public E remove() {
        return remove(size() - 1);
    }
}
