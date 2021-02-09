package com.sd.ds;

import java.util.Iterator;
import java.util.List;

public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {
    protected Node<E> root = null;
    protected int size = 0;

    public LinkedBinaryTree() {
    }

    public LinkedBinaryTree(E e) {
        addRoot(e);
    }

    public LinkedBinaryTree(Iterator<E> iterator) {
        root = fromIterator(iterator);
    }

    private Node<E> fromIterator(Iterator<E> iterator) {
        if (!iterator.hasNext()) {
            return null;
        }
        E e = iterator.next();
        if (e == null) {
            return null;
        }
        Node<E> left = fromIterator(iterator);
        Node<E> right = fromIterator(iterator);
        Node<E> node = new Node<>(e, null, left, right);
        if (left != null) {
            left.setParent(node);
        }
        if (right != null) {
            right.setParent(node);
        }
        size++;
        return node;
    }

    public LinkedBinaryTree(List<E> inList, List<E> postList) {
        int size = inList.size();
        root = fromInPostTraverse(inList, postList, 0, size - 1, 0, size - 1);
    }

    private Node<E> fromInPostTraverse(List<E> inList, List<E> postList, int inLeft, int inRight, int postLeft, int postRight) {
        int size = inRight - inLeft + 1;
        if (size <= 0) {
            return null;
        }
        E rootVal = postList.get(postRight);
        int midIndex = inList.indexOf(rootVal);
        Node<E> left = fromInPostTraverse(inList, postList, inLeft, midIndex - 1, postLeft, midIndex - inLeft + postLeft - 1);
        Node<E> right = fromInPostTraverse(inList, postList, midIndex + 1, inRight, midIndex - inLeft + postLeft, postRight - 1);
        Node<E> root = new Node<>(rootVal, null, left, right);
        if (left != null) {
            left.setParent(root);
        }
        if (right != null) {
            right.setParent(root);
        }
        size++;
        return root;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node)) {
            throw new IllegalArgumentException();
        }
        Node<E> node = (Node<E>) p;
        if (node.getParent() == node) {
            throw new IllegalArgumentException();
        }
        return node;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Position<E> root() {
        return root;
    }

    @Override
    public Position<E> parent(com.sd.ds.Position<E> treeNode) throws IllegalArgumentException {
        return validate(treeNode).getParent();
    }

    @Override
    public Position<E> left(com.sd.ds.Position<E> treeNode) throws IllegalArgumentException {
        return validate(treeNode).getLeft();
    }

    @Override
    public Position<E> right(com.sd.ds.Position<E> treeNode) throws IllegalArgumentException {
        return validate(treeNode).getRight();
    }

    public Position<E> addRoot(E e) throws IllegalArgumentException {
        if (!isEmpty()) {
            throw new IllegalArgumentException("this tree is not empty");
        }
        root = createNode(e, null, null, null);
        size = 1;
        return root;
    }

    public Position<E> addLeft(com.sd.ds.Position<E> p, E e) throws IllegalArgumentException {
        Node<E> parent = validate(p);
        if (parent.getLeft() != null) {
            throw new IllegalArgumentException("p already has a left child");
        }
        Node<E> child = createNode(e, parent, null, null);
        parent.setLeft(child);
        size++;
        return child;
    }

    public Position<E> addRight(com.sd.ds.Position<E> p, E e) throws IllegalArgumentException {
        Node<E> parent = validate(p);
        if (parent.getRight() != null) {
            throw new IllegalArgumentException("p already has a right child");
        }
        Node<E> child = createNode(e, parent, null, null);
        parent.setRight(child);
        size++;
        return child;
    }

    public E set(com.sd.ds.Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        E temp = node.getElement();
        node.setElement(e);
        return temp;
    }

    public void attach(com.sd.ds.Position<E> p, LinkedBinaryTree<E> tree1, LinkedBinaryTree<E> tree2) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if (isInternal(p)) {
            throw new IllegalArgumentException();
        }
        size += tree1.size() + tree2.size();
        if (!tree1.isEmpty()) {
            tree1.root.setParent(node);
            node.setLeft(tree1.root);
            tree1.root = null;
            tree1.size = 0;
        }
        if (!tree2.isEmpty()) {
            tree2.root.setParent(node);
            node.setRight(tree2.root);
            tree2.root = null;
            tree2.size = 0;
        }
    }

    public E remove(com.sd.ds.Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if (numChildren(p) == 2) {
            throw new IllegalArgumentException();
        }
        Node<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight());
        if (child != null) {
            child.setParent(node.getParent());
        }
        if (node == root) {
            root = child;
        } else {
            Node<E> parent = node.getParent();
            if (node == parent.getLeft()) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
        }
        size--;
        E temp = node.getElement();
        node.setParent(node);
        return temp;
    }

    protected Node<E> createNode(E element, Node<E> parent, Node<E> left, Node<E> right) {
        return new Node<>(element, parent, left, right);
    }

    protected static class Node<E> implements Position<E> {
        private E element;
        private Node<E> parent;
        private Node<E> left;
        private Node<E> right;

        public Node(E element, Node<E> parent, Node<E> left, Node<E> right) {
            this.element = element;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        @Override
        public E getElement() {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setParent(Node<E> parent) {
            this.parent = parent;
        }

        public Node<E> getLeft() {
            return left;
        }

        public void setLeft(Node<E> left) {
            this.left = left;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setRight(Node<E> right) {
            this.right = right;
        }
    }
}
