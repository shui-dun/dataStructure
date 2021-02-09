package com.sd.ds;

import java.util.NoSuchElementException;

public class ArrayStack<E> implements Stack<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private E[] items;
    private int size = 0;

    public ArrayStack(int capacity) {
        items = (E[]) new Object[capacity];
    }

    public ArrayStack() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == items.length;
    }

    @Override
    public void clear() {
        size = 0;
    }

    public void trimToSize() {
        ensureCapacity(size);
    }

    @Override
    public void push(E data) {
        if (isFull()) {
            ensureCapacity(2 * size + 1);
        }
        items[size++] = data;
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return items[--size];
    }

    @Override
    public E top() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return items[size - 1];
    }

    private void ensureCapacity(int newCapacity) {
        if (newCapacity < size)
            return;
        E[] old = items;
        items = (E[]) new Object[newCapacity];
        System.arraycopy(old, 0, items, 0, size);
    }
}
