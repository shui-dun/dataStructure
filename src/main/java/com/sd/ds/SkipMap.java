package com.sd.ds;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

public class SkipMap<K, V> extends AbstractSortedMap<K, V> {
    private static class Node<K, V> extends MapEntry<K, V> {
        Node<K, V> next;
        Node<K, V> prev;
        Node<K, V> above;
        Node<K, V> below;

        public Node(K key, V value, Node<K, V> next, Node<K, V> prev, Node<K, V> above, Node<K, V> below) {
            super(key, value);
            this.next = next;
            this.prev = prev;
            this.above = above;
            this.below = below;
        }
    }

    public SkipMap(Comparator<K> cmp) {
        super(cmp);
        clear();
    }

    public SkipMap() {
        clear();
    }

    private int size;

    private Node<K, V> leftHighest;

    private Node<K, V> leftLowest;

    private Node<K, V> rightLowest;

    /**
     * 找到小于等于key的最大节点
     */
    private Node<K, V> findNode(K key) {
        Node<K, V> cur = leftHighest;
        while (true) {
            while (enhancedCompare(key, cur.next) >= 0) {
                cur = cur.next;
            }
            if (cur.below == null) {
                return cur;
            } else {
                cur = cur.below;
            }
        }
    }

    @Override
    public Entry<K, V> firstEntry() {
        return leftLowest.next;
    }

    @Override
    public Entry<K, V> lastEntry() {
        return rightLowest.prev;
    }

    @Override
    public Entry<K, V> ceilingEntry(K key) {
        Node<K, V> node = findNode(key);
        if (node.getKey() == null) {
            return node.next;
        }
        if (node.getKey().equals(key)) {
            return node;
        } else {
            Node<K, V> next = node.next;
            if (next.next == null) {
                return null;
            } else {
                return next;
            }
        }
    }

    @Override
    public Entry<K, V> floorEntry(K key) {
        return findNode(key);
    }

    @Override
    public Entry<K, V> lowerEntry(K key) {
        Node<K, V> node = findNode(key);
        if (node.getKey() == null) {
            return null;
        }
        if (node.getKey().equals(key)) {
            Node<K, V> pre = node.prev;
            if (pre.prev == null) {
                return null;
            } else {
                return pre;
            }
        } else {
            return node;
        }
    }

    @Override
    public Entry<K, V> higherEntry(K key) {
        Node<K, V> node = findNode(key).next;
        if (node.next == null) {
            return null;
        }
        return node;
    }

    private class SkipListIter implements Iterable<Entry<K, V>> {
        private Node<K, V> end;

        private Node<K, V> current;

        public SkipListIter(Node<K, V> begin, Node<K, V> end) {
            this.end = end;
            this.current = begin;
        }

        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new Iterator<Entry<K, V>>() {
                @Override
                public boolean hasNext() {
                    return current != end.next;
                }

                @Override
                public Entry<K, V> next() {
                    current = current.next;
                    return current.prev;
                }
            };
        }
    }

    @Override
    public Iterable<Entry<K, V>> subMap(K key1, K key2) {
        return new SkipListIter((Node<K, V>) floorEntry(key1), (Node<K, V>) lowerEntry(key2));
    }

    @Override
    public V get(K key) {
        Node<K, V> node = findNode(key);
        if (node.getKey() != key) {
            return null;
        }
        return node.getValue();
    }

    @Override
    public V put(K key, V value) {
        Node<K, V> nodeLeft = findNode(key);
        if (nodeLeft.getKey() != key) {
            Random random = new Random();
            Node<K, V> nodeRight = nodeLeft.next;
            Node<K, V> nodeBelow = null;
            do {
                Node<K, V> newNode = new Node<>(key, null, nodeRight, nodeLeft, null, nodeBelow);
                nodeRight.prev = nodeLeft.next = newNode;
                if (nodeBelow != null) {
                    nodeBelow.above = newNode;
                } else {
                    newNode.setValue(value);
                }
                while (nodeRight.above == null) {
                    if (nodeRight.next == null) {
                        Node<K, V> newLeftHighest = new Node<>(null, null, null, null, null, leftHighest);
                        Node<K, V> rightHighest = new Node<>(null, null, null, newLeftHighest, null, nodeRight);
                        nodeRight.above = rightHighest;
                        leftHighest.above = newLeftHighest;
                        newLeftHighest.next = rightHighest;
                        leftHighest = newLeftHighest;
                        break;
                    }
                    nodeRight = nodeRight.next;
                }
                nodeRight = nodeRight.above;
                nodeLeft = nodeRight.prev;
                nodeBelow = newNode;
            } while (random.nextBoolean());
            size++;
            return null;
        } else {
            V oldVal = nodeLeft.getValue();
            nodeLeft.setValue(value);
            return oldVal;
        }
    }

    @Override
    public V remove(K key) {
        Node<K, V> node = findNode(key);
        if (node.getKey() != key) {
            return null;
        }
        V oldVal = node.getValue();
        do {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node = node.above;
        } while (node != null);
        size--;
        return oldVal;
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        return new SkipListIter(leftLowest.next, rightLowest.prev);
    }

    private int enhancedCompare(K key1, Node<K, V> node2) {
        if (node2.next == null) {
            return -1;
        }
        return compare(key1, node2);
    }

    @Override
    public void clear() {
        size = 0;
        leftHighest = new Node<>(null, null, null, null, null, null);
        leftHighest.next = new Node<>(null, null, null, leftHighest, null, null);
        leftLowest = leftHighest;
        rightLowest = leftLowest.next;
    }

    @Override
    public int size() {
        return size;
    }
}
