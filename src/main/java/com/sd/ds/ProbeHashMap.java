package com.sd.ds;

import java.util.ArrayList;
import java.util.Arrays;

public class ProbeHashMap<K, V> extends AbstractHashMap<K, V> {
    private MapEntry<K, V>[] table;
    private MapEntry<K, V> defunct = new MapEntry<>(null, null);

    public ProbeHashMap() {

    }

    public ProbeHashMap(int capacity) {
        super(capacity);
    }

    public ProbeHashMap(int capacity, int prime) {
        super(capacity, prime);
    }

    @Override
    protected void createTable() {
        table = (MapEntry<K, V>[]) new MapEntry[capacity];
    }

    private boolean isAvailable(int i) {
        return table[i] == null || table[i] == defunct;
    }

    private int findSlot(int h, K key) {
        int available = -1;
        int i = h;
        do {
            if (isAvailable(i)) {
                if (available == -1) {
                    available = i;
                }
                if (table[i] == null) {
                    break;
                }
            } else if (table[i].getKey().equals(key)) {
                return i;
            }
            i = (i + 1) % capacity;
        } while (i != h);
        return -(available + 1);
    }

    @Override
    protected V bucketGet(int h, K key) {
        int index = findSlot(h, key);
        if (index >= 0) {
            return table[index].getValue();
        } else {
            return null;
        }
    }

    @Override
    protected V bucketPut(int h, K key, V value) {
        int index = findSlot(h, key);
        if (index >= 0) {
            return table[index].setValue(value);
        } else {
            int trueIndex = -index - 1;
            table[trueIndex] = new MapEntry<>(key, value);
            n++;
            return null;
        }
    }

    @Override
    protected V bucketRemove(int h, K key) {
        int index = findSlot(h, key);
        if (index >= 0) {
            V retVal = table[index].getValue();
            n--;
            table[index] = defunct;
            return retVal;
        } else {
            return null;
        }
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K, V>> list = new ArrayList<>(n);
        for (int i = 0; i < table.length; i++) {
            if (!isAvailable(i)) {
                list.add(table[i]);
            }
        }
        return list;
    }

    @Override
    public void clear() {
        n = 0;
        Arrays.fill(table, null);
    }
}
