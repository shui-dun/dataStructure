package ds;

import java.util.NoSuchElementException;

public class LinkedStack<E> implements Stack<E> {
    private Node<E> head;
    private int size = 0;

    public LinkedStack() {
        head = new Node<>(null, null);
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
    public void clear() {
        head.next = null;
        size = 0;
    }

    @Override
    public void push(E data) {
        head.next = new Node<>(data, head.next);
        size++;
    }

    @Override
    public E top() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return head.next.data;
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        E removeVal = head.next.data;
        size--;
        head.next = head.next.next;
        return removeVal;
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
