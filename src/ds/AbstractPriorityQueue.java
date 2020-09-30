package ds;

import java.util.Comparator;

public abstract class AbstractPriorityQueue<K, V> implements PriorityQueue<K, V> {
    protected static class PQEntry<K, V> implements Entry<K, V> {
        private K key;

        private V value;

        public PQEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    private Comparator<K> cmp;

    public AbstractPriorityQueue(Comparator<K> cmp) {
        this.cmp = cmp;
    }

    public AbstractPriorityQueue() {
        this((o1, o2) -> ((Comparable<K>) o1).compareTo(o2));
    }

    protected int compare(Entry<K, V> a, Entry<K, V> b) {
        return cmp.compare(a.getKey(), b.getKey());
    }

}
