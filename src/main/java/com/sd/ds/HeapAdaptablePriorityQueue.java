package com.sd.ds;

import java.util.Collections;
import java.util.Comparator;

public class HeapAdaptablePriorityQueue<K, V> extends HeapPriorityQueue<K, V> implements AdaptablePriorityQueue<K, V> {
    protected static class AdaptablePQEntry<K, V> extends PQEntry<K, V> {
        private int index;

        public AdaptablePQEntry(K key, V value, int index) {
            super(key, value);
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public HeapAdaptablePriorityQueue() {
    }

    public HeapAdaptablePriorityQueue(Comparator<K> cmp) {
        super(cmp);
    }

    public HeapAdaptablePriorityQueue(K[] keys, V[] values) {
        super(keys, values);
    }

    public HeapAdaptablePriorityQueue(K[] keys, V[] values, Comparator<K> cmp) {
        super(keys, values, cmp);
    }

    @Override
    protected void bottomUp(K[] keys, V[] values) {
        int len = keys.length;
        if (len != values.length) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < len; i++) {
            heap.add(new AdaptablePQEntry<>(keys[i], values[i], i));
        }
        for (int i = parent(size() - 1); i >= 0; i--) {
            downheap(i);
        }
    }

    protected AdaptablePQEntry<K, V> validate(Entry<K, V> entry) throws IllegalArgumentException {
        if (!(entry instanceof AdaptablePQEntry)) {
            throw new IllegalArgumentException();
        }
        AdaptablePQEntry<K, V> locator = (AdaptablePQEntry<K, V>) entry;
        int j = locator.getIndex();
        if (j >= heap.size() || heap.get(j) != locator) {
            throw new IllegalArgumentException();
        }
        return locator;
    }

    protected void swap(int i, int j) {
        Collections.swap(heap, i, j);
        ((AdaptablePQEntry<K, V>) heap.get(i)).setIndex(i);
        ((AdaptablePQEntry<K, V>) heap.get(j)).setIndex(j);
    }

    protected void bubble(int j) {
        if (j > 0 && compare(heap.get(j), heap.get(parent(j))) < 0) {
            upheap(j);
        } else {
            downheap(j);
        }
    }

    @Override
    public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
        Entry<K, V> newest = new AdaptablePQEntry<>(key, value, heap.size());
        heap.add(newest);
        upheap(size() - 1);
        return newest;
    }

    @Override
    public void remove(Entry<K, V> entry) {
        AdaptablePQEntry<K, V> locator = validate(entry);
        int j = locator.getIndex();
        if (j == heap.size() - 1) {
            heap.remove(j);
        } else {
            swap(j, heap.size() - 1);
            heap.remove(heap.size() - 1);
            bubble(j);
        }
    }

    @Override
    public void replaceKey(Entry<K, V> entry, K key) {
        AdaptablePQEntry<K, V> locator = validate(entry);
        locator.setKey(key);
        bubble(locator.getIndex());
    }

    @Override
    public void replaceValue(Entry<K, V> entry, V value) {
        AdaptablePQEntry<K, V> locator = validate(entry);
        locator.setValue(value);
    }
}
