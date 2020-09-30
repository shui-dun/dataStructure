package ds;

import java.util.NoSuchElementException;

public class LinkedQueue<E> implements Queue<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public LinkedQueue() {
        clear();
    }

    @Override
    public void clear() {
        head = new Node<>(null, null);
        tail = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E front() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return head.next.data;
    }

    @Override
    public E back() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return tail.data;
    }

    @Override
    public void push(E data) {
        if (tail != null) {
            tail.next = new Node<>(data, null);
            tail = tail.next;
        } else {
            tail = new Node<>(data, null);
            head.next = tail;
        }
        size++;
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        E removedData = head.next.data;
        head.next = head.next.next;
        size--;
        if (isEmpty()) {
            tail = null;
        }
        return removedData;
    }

    private static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }
}
