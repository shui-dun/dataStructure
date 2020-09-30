package ds;

import java.util.Comparator;

public abstract class AbstractSortedMap<K, V> extends AbstractMap<K, V> implements SortedMap<K, V> {
    private Comparator<K> cmp;

    public AbstractSortedMap(Comparator<K> cmp) {
        this.cmp = cmp;
    }

    public AbstractSortedMap() {
        this((o1, o2) -> ((Comparable<K>) o1).compareTo(o2));
    }

    protected int compare(Entry<K, V> entry1, Entry<K, V> entry2) {
        return cmp.compare(entry1.getKey(), entry2.getKey());
    }

    protected int compare(K a, Entry<K, V> b) {
        return cmp.compare(a, b.getKey());
    }

    protected int compare(Entry<K, V> a, K b) {
        return cmp.compare(a.getKey(), b);
    }

    protected int compare(K a, K b) {
        return cmp.compare(a, b);
    }

}
