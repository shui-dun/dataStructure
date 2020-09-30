package ds;

import java.util.Iterator;

public abstract class AbstractMap<K, V> implements Map<K, V> {
    protected static class MapEntry<K, V> implements Entry<K, V> {
        private K key;
        private V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        @Override
        public V getValue() {
            return value;
        }

        public V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }
    }

    public Iterable<K> keySet() {
        return () -> new Iterator<K>() {
            private Iterator<Entry<K, V>> entries = entrySet().iterator();

            @Override
            public boolean hasNext() {
                return entries.hasNext();
            }

            @Override
            public K next() {
                return entries.next().getKey();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public Iterable<V> values() {
        return () -> new Iterator<V>() {
            private Iterator<Entry<K, V>> entries = entrySet().iterator();

            @Override
            public boolean hasNext() {
                return entries.hasNext();
            }

            @Override
            public V next() {
                return entries.next().getValue();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }


}
