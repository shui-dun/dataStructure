package com.sd.ds;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GenericDisjointSet<E> implements DataStructure {
    private Map<E, Integer> map = new HashMap<>();
    private IntDisjointSet set;

    public GenericDisjointSet(Collection<E> items) {
        set = new IntDisjointSet(items.size());
        int i = 0;
        for (E item : items) {
            map.put(item, i++);
        }
    }

    public boolean inSameSet(E a, E b) {
        return set.inSameSet(map.get(a), map.get(b));
    }

    public int find(E pos) {
        return set.find(map.get(pos));
    }

    public boolean union(E a, E b) {
        return set.union(map.get(a), map.get(b));
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return set.size();
    }
}
