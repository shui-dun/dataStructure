package ds;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class BinarySearchTree<E extends Comparable<? super E>> extends LinkedBinaryTree<E> implements SortedSet<E> {

    @Override
    public boolean contains(E element) {
        if (element == null) {
            return false;
        }
        return contains(root, element);
    }

    private boolean contains(Node<E> node, E element) {
        if (node == null) {
            return false;
        }
        int compareResult = element.compareTo(node.getElement());
        if (compareResult > 0) {
            return contains(node.getRight(), element);
        } else if (compareResult < 0) {
            return contains(node.getLeft(), element);
        } else {
            return true;
        }
    }

    @Override
    public E first() {
        Node<E> node = root;
        if (node == null) {
            return null;
        }
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node.getElement();
    }

    @Override
    public E last() {
        Node<E> node = root;
        if (node == null) {
            return null;
        }
        while (node.getRight() != null) {
            node = node.getRight();
        }
        return node.getElement();
    }

    @Override
    public E floor(E element) {
        if (element == null || root == null) {
            return null;
        }
        return floor(root, element).getElement();
    }

    private Node<E> floor(Node<E> node, E element) {
        if (node == null) {
            return null;
        }
        int compareResult = node.getElement().compareTo(element);
        if (isExternal(node)) {
            if (compareResult > 0) {
                return null;
            } else {
                return node;
            }
        } else {
            if (compareResult > 0) {
                return floor(node.getLeft(), element);
            } else if (compareResult == 0) {
                return node;
            } else {
                Node<E> rightResult = floor(node.getRight(), element);
                if (rightResult == null) {
                    return node;
                } else {
                    return rightResult;
                }
            }
        }
    }

    @Override
    public E ceiling(E element) {
        if (element == null || root == null) {
            return null;
        }
        return ceiling(root, element).getElement();
    }

    private Node<E> ceiling(Node<E> node, E element) {
        if (node == null) {
            return null;
        }
        int compareResult = node.getElement().compareTo(element);
        if (isExternal(node)) {
            if (compareResult < 0) {
                return null;
            } else {
                return node;
            }
        } else {
            if (compareResult > 0) {
                Node<E> leftResult = ceiling(node.getLeft(), element);
                if (leftResult == null) {
                    return node;
                } else {
                    return leftResult;
                }
            } else if (compareResult == 0) {
                return node;
            } else {
                return ceiling(node.getRight(), element);
            }
        }
    }

    @Override
    public E lower(E element) {
        if (element == null || root == null) {
            return null;
        }
        return lower(root, element).getElement();
    }

    private Node<E> lower(Node<E> node, E element) {
        if (node == null) {
            return null;
        }
        int compareResult = node.getElement().compareTo(element);
        if (isExternal(node)) {
            if (compareResult >= 0) {
                return null;
            } else {
                return node;
            }
        } else {
            if (compareResult >= 0) {
                return lower(node.getLeft(), element);
            } else {
                Node<E> rightResult = lower(node.getRight(), element);
                if (rightResult == null) {
                    return node;
                } else {
                    return rightResult;
                }
            }
        }
    }

    @Override
    public E higher(E element) {
        if (element == null || root == null) {
            return null;
        }
        return higher(root, element).getElement();
    }

    private Node<E> higher(Node<E> node, E element) {
        if (node == null) {
            return null;
        }
        int compareResult = node.getElement().compareTo(element);
        if (isExternal(node)) {
            if (compareResult <= 0) {
                return null;
            } else {
                return node;
            }
        } else {
            if (compareResult > 0) {
                Node<E> leftResult = higher(node.getLeft(), element);
                if (leftResult == null) {
                    return node;
                } else {
                    return leftResult;
                }
            } else {
                return higher(node.getRight(), element);
            }
        }
    }

    @Override
    public Iterable<E> subSet(E begin, E end) {
        return () -> new Iterator<E>() {
            Node<E> endNode = null;
            Node<E> curNode = null;

            {
                curNode = ceiling(root, begin);
                endNode = ceiling(root, end);
            }

            @Override
            public boolean hasNext() {
                return curNode != endNode;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Node<E> pre = curNode;
                if (curNode.getRight() != null) {
                    curNode = curNode.getRight();
                    while (curNode.getLeft() != null) {
                        curNode = curNode.getLeft();
                    }
                } else {
                    Node<E> parent = curNode.getParent();
                    while (parent != null && parent.getRight() == curNode) {
                        curNode = parent;
                        parent = curNode.getParent();
                    }
                    curNode = parent;
                }
                return pre.getElement();
            }
        };
    }

    @Override
    public void insert(E element) {
        if (element == null) {
            return;
        }
        root = insert(root, element);
    }

    private Node<E> insert(Node<E> node, E element) {
        if (node == null) {
            size++;
            return new Node<>(element, null, null, null);
        } else {
            int compareResult = node.getElement().compareTo(element);
            if (compareResult < 0) {
                node.setRight(insert(node.getRight(), element));
                node.getRight().setParent(node);
            } else if (compareResult > 0) {
                node.setLeft(insert(node.getLeft(), element));
                node.getLeft().setParent(node);
            }
            return node;
        }
    }

    @Override
    public void remove(E element) {
        root = remove(root, element);
    }

    @Override
    public void addAll(E... elements) {
        for (E e : elements) {
            insert(e);
        }
    }

    @Override
    public void addAll(Set<E> set) {
        for (E element : set) {
            insert(element);
        }
    }

    @Override
    public void removeAll(Set<E> set) {
        for (E element : set) {
            remove(element);
        }
    }

    @Override
    public void retainAll(Set<E> set) {
        List<E> removedList = new LinkedList<>();
        for (E element : this) {
            if (!set.contains(element)) {
                removedList.add(element);
            }
        }
        for (E element : removedList) {
            remove(element);
        }
    }

    private Node<E> remove(Node<E> node, E element) {
        if (node == null) {
            return null;
        }
        int compareResult = node.getElement().compareTo(element);
        if (compareResult > 0) {
            node.setLeft(remove(node.getLeft(), element));
            if (node.getLeft() != null) {
                node.getLeft().setParent(node);
            }
            return node;
        } else if (compareResult < 0) {
            node.setRight(remove(node.getRight(), element));
            if (node.getRight() != null) {
                node.getRight().setParent(node);
            }
            return node;
        } else {
            size--;
            if (isExternal(node)) {
                return null;
            } else if (node.getRight() == null) {
                return node.getLeft();
            } else if (node.getLeft() == null) {
                return node.getRight();
            } else {
                Node<E> replace = node.getLeft();
                while (replace.getRight() != null) {
                    replace = replace.getRight();
                }
                node.setElement(replace.getElement());
                Node<E> parent = replace.getParent();
                if (replace == parent.getLeft()) {
                    parent.setLeft(null);
                } else {
                    parent.setRight(null);
                }
                return node;
            }
        }
    }
}
