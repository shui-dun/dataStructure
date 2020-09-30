package ds;

public interface List<E> extends Iterable<E>, DataStructure {
    E get(int idx);

    E set(int idx, E newVal);

    void add(E x);

    void add(int idx, E x);

    E remove(int idx);

    E remove();
}
