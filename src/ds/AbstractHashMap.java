package ds;

import java.util.ArrayList;
import java.util.Random;

public abstract class AbstractHashMap<K, V> extends AbstractMap<K, V> {
    protected int n = 0;
    protected int capacity;
    private int prime;
    private long scale, shift;

    public AbstractHashMap(int capacity, int prime) {
        this.capacity = capacity;
        this.prime = prime;
        Random random = new Random();
        scale = random.nextInt(prime - 1) + 1;
        shift = random.nextInt(prime);
        createTable();
    }

    public AbstractHashMap(int capacity) {
        this(capacity, 109345121);
    }

    public AbstractHashMap() {
        this(17);
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public V get(K key) {
        return bucketGet(hashvalue(key), key);
    }

    @Override
    public V remove(K key) {
        return bucketRemove(hashvalue(key), key);
    }

    @Override
    public V put(K key, V value) {
        V answer = bucketPut(hashvalue(key), key, value);
        if (n > capacity / 2) {
            resize(2 * capacity + 1);
        }
        return answer;
    }

    private int hashvalue(K key) {
        return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
    }

    private void resize(int newCapacity) {
        ArrayList<Entry<K, V>> buffer = new ArrayList<>(n);
        for (Entry<K, V> entry : entrySet()) {
            buffer.add(entry);
        }
        capacity = newCapacity;
        createTable();
        n = 0;
        for (Entry<K, V> entry : buffer) {
            put(entry.getKey(), entry.getValue());
        }
    }

    protected abstract void createTable();

    protected abstract V bucketGet(int h, K key);

    protected abstract V bucketPut(int h, K key, V value);

    protected abstract V bucketRemove(int h, K key);
}
