package com.sd.ds;

public interface Deque<E> extends Queue<E> {
    void pushFirst(E data);

    E popLast();
}
