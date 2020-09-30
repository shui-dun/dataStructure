package ds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChainHashMap<K, V> extends AbstractHashMap<K, V> {
    private UnsortedTableMap<K, V>[] table;

    public ChainHashMap() {
    }

    public ChainHashMap(int capacity) {
        super(capacity);
    }

    public ChainHashMap(int capacity, int prime) {
        super(capacity, prime);
    }

    @Override
    protected void createTable() {
        table = (UnsortedTableMap<K, V>[]) new UnsortedTableMap[capacity];
    }

    @Override
    protected V bucketGet(int h, K key) {
        UnsortedTableMap<K, V> bucket = table[h];
        if (bucket == null) {
            return null;
        }
        return bucket.get(key);
    }

    @Override
    protected V bucketPut(int h, K key, V value) {
        UnsortedTableMap<K, V> bucket = table[h];
        if (bucket == null) {
            table[h] = new UnsortedTableMap<>();
            table[h].put(key, value);
            n++;
            return null;
        }
        int oldSize = bucket.size();
        V retVal = bucket.put(key, value);
        if (bucket.size() != oldSize) {
            n++;
        }
        return retVal;
    }

    @Override
    protected V bucketRemove(int h, K key) {
        UnsortedTableMap<K, V> bucket = table[h];
        if (bucket == null) {
            return null;
        }
        int oldSize = bucket.size();
        V retVal = bucket.remove(key);
        if (bucket.size() != oldSize) {
            n--;
        }
        return retVal;
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        List<Entry<K, V>> list = new ArrayList<>(n);
        for (UnsortedTableMap<K, V> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket.entrySet()) {
                    list.add(entry);
                }
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
