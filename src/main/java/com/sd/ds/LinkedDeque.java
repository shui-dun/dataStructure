package com.sd.ds;

import java.util.NoSuchElementException;

public class LinkedDeque<E> implements Deque<E> {

    private Node<E> head;

    private Node<E> tail;

    private int size;

    public LinkedDeque() {
        head = new Node<>(null, null, null);
        tail = new Node<>(null, head, null);
        head.next = tail;
        size = 0;
    }

    @Override
    public void pushFirst(E data) {
        Node<E> newNode = new Node<>(data, head, head.next);
        head.next.prev = newNode;
        head.next = newNode;
        size++;
    }

    @Override
    public E popLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        E removeData = tail.prev.data;
        tail.prev = tail.prev.prev;
        tail.prev.next = tail;
        size--;
        return removeData;
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
        return tail.prev.data;
    }

    @Override
    public void push(E data) {
        Node<E> newNode = new Node<>(data, tail.prev, tail);
        tail.prev.next = newNode;
        tail.prev = newNode;
        size++;
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        E data = head.next.data;
        head.next = head.next.next;
        head.next.prev = head;
        size--;
        return data;
    }

    @Override
    public void clear() {
        head.next = tail;
        tail.prev = head;
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

    private static class Node<E> {
        private E data;
        private Node<E> prev;
        private Node<E> next;

        public Node(E data, Node<E> prev, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }
}
