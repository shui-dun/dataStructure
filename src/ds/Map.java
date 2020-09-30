package ds;

public interface Map<K, V> extends DataStructure {
    V get(K key);

    V put(K key, V value);

    V remove(K key);

    Iterable<K> keySet();

    Iterable<V> values();

    Iterable<Entry<K, V>> entrySet();
}
