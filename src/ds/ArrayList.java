package ds;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<E> extends AbstractList<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private int size;
    private E[] items;

    public ArrayList() {
        clear();
    }

    public ArrayList(int capacity) {
        size = 0;
        ensureCapacity(capacity);
    }

    @Override
    public void clear() {
        size = 0;
        ensureCapacity(DEFAULT_CAPACITY);
    }

    @Override
    public int size() {
        return size;
    }

    public void trimToSize() {
        ensureCapacity(size);
    }

    @Override
    public E get(int idx) {
        if (idx < 0 || idx >= size) {
            return null;
        }
        return items[idx];
    }

    @Override
    public E set(int idx, E newVal) {
        if (idx < 0 || idx >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        E old = items[idx];
        items[idx] = newVal;
        return old;
    }

    public boolean ensureCapacity(int newCapacity) {
        if (newCapacity < size)
            return false;
        E[] old = items;
        items = (E[]) new Object[newCapacity];
        if (old != null) {
            System.arraycopy(old, 0, items, 0, size);
        }
        return true;
    }

    @Override
    public void add(int idx, E x) {
        if (items.length == size) {
            ensureCapacity(size * 2 + 1);
        }
        System.arraycopy(items, idx, items, idx + 1, size - idx);
        items[idx] = x;
        size++;
    }

    @Override
    public E remove(int idx) {
        E removedItem = items[idx];
        System.arraycopy(items, idx + 1, items, idx, size - 1 - idx);
        size--;
        return removedItem;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator<E> {
        private int current = 0;

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return items[current++];
        }

        @Override
        public void remove() {
            ArrayList.this.remove(--current);
        }
    }
}
