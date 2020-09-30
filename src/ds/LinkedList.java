package ds;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<E> extends AbstractList<E> {
    private int size;

    private int modCount = 0;

    private Node<E> beginMarker;

    private Node<E> endMarker;

    public LinkedList() {
        clear();
    }

    @Override
    public void clear() {
        beginMarker = new Node<E>(null, null, null);
        endMarker = new Node<E>(null, beginMarker, null);
        beginMarker.next = endMarker;
        size = 0;
        modCount++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(int idx, E x) {
        addBefore(getNode(idx, 0, size), x);
    }

    @Override
    public E get(int idx) {
        return getNode(idx).data;
    }

    @Override
    public E set(int idx, E newVal) {
        Node<E> p = getNode(idx);
        E oldVal = p.data;
        p.data = newVal;
        return oldVal;
    }

    @Override
    public E remove(int idx) {
        return remove(getNode(idx));
    }

    private E remove(Node<E> p) {
        p.next.prev = p.prev;
        p.prev.next = p.next;
        size--;
        modCount++;
        return p.data;
    }

    private void addBefore(Node<E> p, E x) {
        Node<E> newNode = new Node<>(x, p.prev, p);
        newNode.prev.next = newNode;
        p.prev = newNode;
        size++;
        modCount++;
    }

    private Node<E> getNode(int idx) {
        return getNode(idx, 0, size - 1);
    }

    private Node<E> getNode(int idx, int lower, int upper) {
        Node<E> p;
        if (idx < lower || idx > upper) {
            throw new IndexOutOfBoundsException();
        }
        if (idx < size / 2) {
            p = beginMarker.next;
            for (int i = 0; i < idx; i++) {
                p = p.next;
            }
        } else {
            p = endMarker;
            for (int i = size; i > idx; i--) {
                p = p.prev;
            }
        }
        return p;
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    private static class Node<T> {
        private T data;
        private Node<T> prev;
        private Node<T> next;

        public Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    private class LinkedListIterator implements Iterator<E> {
        private Node<E> current = beginMarker.next;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;

        @Override
        public boolean hasNext() {
            return current != endMarker;
        }

        @Override
        public E next() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E nextItem = current.data;
            okToRemove = true;
            current = current.next;
            return nextItem;
        }

        @Override
        public void remove() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (!okToRemove) {
                throw new IllegalStateException();
            }
            LinkedList.this.remove(current.prev);
            expectedModCount++;
            okToRemove = false;
        }
    }
}
