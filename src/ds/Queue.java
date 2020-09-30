package ds;

public interface Queue<E> extends DataStructure {
    E front();

    E back();

    void push(E data);

    E pop();
}
