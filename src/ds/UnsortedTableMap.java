package ds;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class UnsortedTableMap<K, V> extends AbstractMap<K, V> {

    private ArrayList<MapEntry<K, V>> table = new ArrayList<>();

    public UnsortedTableMap() {

    }

    private int findIndex(K key) {
        int n = table.size();
        for (int i = 0; i < n; i++) {
            if (table.get(i).getKey().equals(key)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return table.size();
    }

    @Override
    public V get(K key) {
        int i = findIndex(key);
        if (i == -1) {
            return null;
        }
        return table.get(i).getValue();
    }

    @Override
    public V put(K key, V value) {
        int i = findIndex(key);
        if (i == -1) {
            table.add(new MapEntry<>(key, value));
            return null;
        }
        return table.get(i).setValue(value);
    }

    @Override
    public V remove(K key) {
        int i = findIndex(key);
        if (i == -1) {
            return null;
        }
        int n = size();
        V answer = table.get(i).getValue();
        table.set(i, table.get(n - 1));
        table.remove(n - 1);
        return answer;
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        return () -> new Iterator<Entry<K, V>>() {
            private int position = 0;

            @Override
            public boolean hasNext() {
                return position != size();
            }

            @Override
            public Entry<K, V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return table.get(position++);
            }
        };
    }

    @Override
    public void clear() {
        table.clear();
    }
}
