package com.sd.ds;

public abstract class AbstractBinaryTree<E> extends AbstractTree<E> implements BinaryTree<E> {
    @Override
    public Position<E> sibling(Position<E> treeNode) {
        Position<E> parent = parent(treeNode);
        if (parent == null) {
            return null;
        }
        if (treeNode == left(parent)) {
            return right(parent);
        } else {
            return left(parent);
        }
    }

    @Override
    public int numChildren(Position<E> treeNode) {
        int count = 0;
        if (left(treeNode) != null) {
            count++;
        }
        if (right(treeNode) != null) {
            count++;
        }
        return count;
    }

    @Override
    public Iterable<Position<E>> children(Position<E> treeNode) {
        List<Position<E>> snapshot = new ArrayList<>(2);
        if (left(treeNode) != null) {
            snapshot.add(left(treeNode));
        }
        if (right(treeNode) != null) {
            snapshot.add(right(treeNode));
        }
        return snapshot;
    }

    private void inorderSubtree(Position<E> p, List<Position<E>> snapshot) {
        if (left(p) != null) {
            inorderSubtree(left(p), snapshot);
        }
        snapshot.add(p);
        if (right(p) != null) {
            inorderSubtree(right(p), snapshot);
        }
    }

    @Override
    public Iterable<Position<E>> inorder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty()) {
            inorderSubtree(root(), snapshot);
        }
        return snapshot;
    }

    @Override
    public Iterable<Position<E>> positions() {
        return inorder();
    }
}
