package ds;

public interface AdaptablePriorityQueue<K, V> extends PriorityQueue<K, V> {
    void remove(Entry<K, V> entry);

    void replaceKey(Entry<K, V> entry, K key);

    void replaceValue(Entry<K, V> entry, V value);
}
