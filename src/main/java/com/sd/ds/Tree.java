package com.sd.ds;

public interface Tree<E> extends Iterable<E>, DataStructure {
    Iterable<Position<E>> positions();

    Position<E> root();

    Position<E> parent(Position<E> treeNode) throws IllegalArgumentException;

    Iterable<Position<E>> children(Position<E> treeNode) throws IllegalArgumentException;

    int numChildren(Position<E> treeNode) throws IllegalArgumentException;

    boolean isInternal(Position<E> treeNode) throws IllegalArgumentException;

    boolean isExternal(Position<E> treeNode) throws IllegalArgumentException;

    boolean isRoot(Position<E> treeNode) throws IllegalArgumentException;

    int depth(Position<E> treeNode) throws IllegalArgumentException;

    int height(Position<E> treeNode) throws IllegalArgumentException;

    Iterable<Position<E>> preorder();

    Iterable<Position<E>> postorder();

    Iterable<Position<E>> breadthfirst();

    void draw();

    void draw(Position<E> p);

    String parenthesize();

    String parenthesize(Position<E> p);
}
