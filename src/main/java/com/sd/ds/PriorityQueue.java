package com.sd.ds;

public interface PriorityQueue<K, V> extends DataStructure {
    Entry<K, V> insert(K key, V value) throws IllegalArgumentException;

    Entry<K, V> min();

    Entry<K, V> removeMin();
}
