package ds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HeapPriorityQueue<K, V> extends AbstractPriorityQueue<K, V> {
    protected ArrayList<Entry<K, V>> heap = new ArrayList<>();

    public HeapPriorityQueue() {
        super();
    }

    public HeapPriorityQueue(Comparator<K> cmp) {
        super(cmp);
    }

    public HeapPriorityQueue(K[] keys, V[] values) {
        super();
        bottomUp(keys, values);
    }

    public HeapPriorityQueue(K[] keys, V[] values, Comparator<K> cmp) {
        super(cmp);
        bottomUp(keys, values);
    }

    protected void bottomUp(K[] keys, V[] values) {
        int len = keys.length;
        if (len != values.length) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < len; i++) {
            heap.add(new PQEntry<>(keys[i], values[i]));
        }
        for (int i = parent(size() - 1); i >= 0; i--) {
            downheap(i);
        }
    }

    protected int parent(int j) {
        return (j - 1) / 2;
    }

    protected int left(int j) {
        return 2 * j + 1;
    }

    protected int right(int j) {
        return 2 * j + 2;
    }

    protected boolean hasLeft(int j) {
        return left(j) < heap.size();
    }

    protected boolean hasRight(int j) {
        return right(j) < heap.size();
    }

    protected void swap(int i, int j) {
        Collections.swap(heap, i, j);
    }

    protected void upheap(int j) {
        while (j > 0) {
            int p = parent(j);
            if (compare(heap.get(j), heap.get(p)) >= 0)
                break;
            swap(j, p);
            j = p;
        }
    }

    protected void downheap(int j) {
        while (hasLeft(j)) {
            int leftIndex = left(j);
            int smallIndex = leftIndex;
            if (hasRight(j) && compare(heap.get(leftIndex), heap.get(right(j))) > 0)
                smallIndex = right(j);
            if (compare(heap.get(smallIndex), heap.get(j)) >= 0)
                break;
            swap(smallIndex, j);
            j = smallIndex;
        }
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public Entry<K, V> min() {
        if (heap.isEmpty()) {
            return null;
        }
        return heap.get(0);
    }

    @Override
    public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
        Entry<K, V> newest = new PQEntry<>(key, value);
        heap.add(newest);
        upheap(size() - 1);
        return newest;
    }

    @Override
    public Entry<K, V> removeMin() {
        if (heap.isEmpty()) {
            return null;
        }
        swap(0, heap.size() - 1);
        Entry<K, V> retVal = heap.remove(heap.size() - 1);
        downheap(0);
        return retVal;
    }

    @Override
    public void clear() {
        heap.clear();
    }
}
