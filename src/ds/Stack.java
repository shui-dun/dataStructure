package ds;

public interface Stack<E> extends DataStructure {
    void push(E data);

    E pop();

    E top();
}
