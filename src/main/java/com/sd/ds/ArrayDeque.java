package com.sd.ds;

import java.util.NoSuchElementException;

public class ArrayDeque<E> implements Deque<E> {
    private E[] items;
    private int front = 0;
    private int back = 0;
    private int size = 0;

    public ArrayDeque(int capacity) {
        items = (E[]) new Object[capacity];
    }

    @Override
    public void clear() {
        front = back = size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    public boolean isFull() {
        return size == items.length;
    }

    @Override
    public E front() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return items[front];
    }

    @Override
    public E back() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return items[back];
    }

    @Override
    public void push(E data) {
        if (isFull()) {
            throw new NoSuchElementException();
        }
        if (!isEmpty()) {
            back = (back + 1) % items.length;
        }
        items[back] = data;
        size++;
    }

    @Override
    public void pushFirst(E data) {
        if (isFull()) {
            throw new NoSuchElementException();
        }
        if (!isEmpty()) {
            front = (front - 1 + items.length) % items.length;
        }
        items[front] = data;
        size++;
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        E removedData = items[front];
        if (size != 1) {
            front = (front + 1) % items.length;
        }
        size--;
        return removedData;
    }

    @Override
    public E popLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        E removedData = items[back];
        if (size != 1) {
            back = (back - 1 + items.length) % items.length;
        }
        size--;
        return removedData;
    }
}
