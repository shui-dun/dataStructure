package com.sd.ds;

import java.util.Comparator;
import java.util.LinkedList;

public class UnsortedPriorityQueue<K, V> extends AbstractPriorityQueue<K, V> {
    private LinkedList<Entry<K, V>> list = new LinkedList<>();

    public UnsortedPriorityQueue() {
        super();
    }

    public UnsortedPriorityQueue(Comparator<K> cmp) {
        super(cmp);
    }

    @Override
    public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
        Entry<K, V> newest = new PQEntry<>(key, value);
        list.add(newest);
        return newest;
    }

    @Override
    public Entry<K, V> min() {
        if (list.isEmpty()) {
            return null;
        }
        Entry<K, V> small = list.getFirst();
        for (Entry<K, V> walk : list) {
            if (compare(walk, small) < 0) {
                small = walk;
            }
        }
        return small;
    }

    @Override
    public Entry<K, V> removeMin() {
        Entry<K, V> min = min();
        if (min != null) {
            list.remove(min);
        }
        return min;
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public int size() {
        return list.size();
    }
}
