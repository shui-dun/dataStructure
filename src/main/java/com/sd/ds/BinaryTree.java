package com.sd.ds;

public interface BinaryTree<E> extends Tree<E> {
    Position<E> left(Position<E> treeNode) throws IllegalArgumentException;

    Position<E> right(Position<E> treeNode) throws IllegalArgumentException;

    Position<E> sibling(Position<E> treeNode) throws IllegalArgumentException;

    Iterable<Position<E>> inorder();
}
