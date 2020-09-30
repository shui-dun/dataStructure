package ds;

public interface DataStructure {
    void clear();

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }


}
