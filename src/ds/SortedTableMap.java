package ds;

import java.util.ArrayList;
import java.util.Comparator;

public class SortedTableMap<K, V> extends AbstractSortedMap<K, V> {
    private ArrayList<MapEntry<K, V>> table = new ArrayList<>();

    public SortedTableMap() {

    }

    public SortedTableMap(Comparator<K> cmp) {
        super(cmp);
    }

    private int findIndex(K key, int low, int high) {
        if (low > high) {
            return high + 1;
        }
        int mid = (low + high) / 2;
        int cmpResult = compare(key, table.get(mid));
        if (cmpResult < 0) {
            return findIndex(key, low, mid - 1);
        } else if (cmpResult > 0) {
            return findIndex(key, mid + 1, high);
        } else {
            return mid;
        }
    }

    private int findIndex(K key) {
        return findIndex(key, 0, table.size() - 1);
    }

    @Override
    public void clear() {
        table.clear();
    }

    @Override
    public int size() {
        return table.size();
    }

    @Override
    public V get(K key) {
        int i = findIndex(key);
        if (i == size() || compare(table.get(i).getKey(), key) != 0) {
            return null;
        }
        return table.get(i).getValue();
    }

    @Override
    public V put(K key, V value) {
        int i = findIndex(key);
        if (i < size() && compare(table.get(i).getKey(), key) == 0) {
            return table.get(i).setValue(value);
        }
        table.add(i, new MapEntry<>(key, value));
        return null;
    }

    @Override
    public V remove(K key) {
        int i = findIndex(key);
        if (i == size() || compare(table.get(i).getKey(), key) != 0) {
            return null;
        }
        return table.remove(i).getValue();
    }

    private Entry<K, V> safeEntry(int i) {
        if (i < 0 || i >= table.size()) {
            return null;
        }
        return table.get(i);
    }

    @Override
    public Entry<K, V> firstEntry() {
        return safeEntry(0);
    }

    @Override
    public Entry<K, V> lastEntry() {
        return safeEntry(table.size() - 1);
    }

    @Override
    public Entry<K, V> ceilingEntry(K key) {
        return safeEntry(findIndex(key));
    }

    @Override
    public Entry<K, V> floorEntry(K key) {
        int j = findIndex(key);
        if (j == size() || !key.equals(table.get(j).getKey()))
            j--;    // look one earlier (unless we had found a perfect match)
        return safeEntry(j);
    }

    @Override
    public Entry<K, V> lowerEntry(K key) {
        return safeEntry(findIndex(key) - 1);
    }

    @Override
    public Entry<K, V> higherEntry(K key) {
        int j = findIndex(key);
        if (j < size() && key.equals(table.get(j).getKey()))
            j++;    // go past exact match
        return safeEntry(j);
    }

    private Iterable<Entry<K, V>> snapshot(int startIndex, K stop) {
        ArrayList<Entry<K, V>> buffer = new ArrayList<>();
        int j = startIndex;
        while (j < table.size() && (stop == null || compare(stop, table.get(j)) > 0))
            buffer.add(table.get(j++));
        return buffer;
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        return snapshot(0, null);
    }

    @Override
    public Iterable<Entry<K, V>> subMap(K fromKey, K toKey) throws IllegalArgumentException {
        return snapshot(findIndex(fromKey), toKey);
    }
}
