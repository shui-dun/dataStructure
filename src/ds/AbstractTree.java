package ds;

import java.util.Iterator;

public abstract class AbstractTree<E> implements Tree<E> {
    @Override
    public boolean isInternal(Position<E> treeNode) {
        return numChildren(treeNode) > 0;
    }

    @Override
    public boolean isExternal(Position<E> treeNode) {
        return numChildren(treeNode) == 0;
    }

    @Override
    public boolean isRoot(Position<E> treeNode) {
        return treeNode == root();
    }

    @Override
    public int depth(Position<E> treeNode) {
        if (isRoot(treeNode)) {
            return 0;
        } else {
            return 1 + depth(parent(treeNode));
        }
    }

    @Override
    public int height(Position<E> treeNode) {
        int h = 0;
        for (Position<E> c : children(treeNode)) {
            h = Math.max(h, 1 + height(c));
        }
        return h;
    }

    @Override
    public Iterator<E> iterator() {
        return new ElementIterator();
    }

    @Override
    public Iterable<Position<E>> positions() {
        return preorder();
    }

    private void preorderSubTree(Position<E> p, List<Position<E>> snapshot) {
        snapshot.add(p);
        for (Position<E> c : children(p)) {
            preorderSubTree(c, snapshot);
        }
    }

    @Override
    public Iterable<Position<E>> preorder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty()) {
            preorderSubTree(root(), snapshot);
        }
        return snapshot;
    }

    private void postorderSubTree(Position<E> p, List<Position<E>> snapshot) {
        for (Position<E> c : children(p)) {
            postorderSubTree(c, snapshot);
        }
        snapshot.add(p);
    }

    @Override
    public Iterable<Position<E>> postorder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty()) {
            postorderSubTree(root(), snapshot);
        }
        return snapshot;
    }

    @Override
    public Iterable<Position<E>> breadthfirst() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty()) {
            Queue<Position<E>> q = new LinkedDeque<>();
            q.push(root());
            while (!q.isEmpty()) {
                Position<E> p = q.pop();
                snapshot.add(p);
                for (Position<E> c : children(p)) {
                    q.push(c);
                }
            }
        }
        return snapshot;
    }

    private void drawSubtree(Position<E> parent, int depth, List<Boolean> list) {
        if (depth != 0) {
            for (int i = 0; i < depth - 1; i++) {
                if (list.get(i)) {
                    System.out.print("     ");
                } else {
                    System.out.print("│    ");
                }
            }
            if (list.get(list.size() - 1)) {
                System.out.print("└─── ");
            } else {
                System.out.print("├─── ");
            }
        }
        System.out.println(parent.getElement());
        Iterable<Position<E>> c = children(parent);
        if (c != null) {
            Iterator<Position<E>> iterator = c.iterator();
            if (iterator.hasNext()) {
                while (true) {
                    Position<E> p = iterator.next();
                    if (!iterator.hasNext()) {
                        list.add(true);
                        drawSubtree(p, depth + 1, list);
                        break;
                    } else {
                        list.add(false);
                        drawSubtree(p, depth + 1, list);
                    }
                }
            }
        }
        if (!list.isEmpty()) {
            list.remove(list.size() - 1);
        }
    }

    @Override
    public void draw() {
        draw(root());
    }

    @Override
    public void draw(Position<E> p) {
        drawSubtree(p, 0, new ArrayList<>());
    }

    @Override
    public String parenthesize() {
        return parenthesize(root());
    }

    @Override
    public String parenthesize(Position<E> p) {
        StringBuilder s = new StringBuilder();
        parenthesizeSubtree(p, s);
        return s.toString();
    }

    private void parenthesizeSubtree(Position<E> p, StringBuilder s) {
        s.append(p.getElement());
        if (isInternal(p)) {
            s.append("(");
            Iterator<Position<E>> iterator = children(p).iterator();
            while (true) {
                Position<E> child = iterator.next();
                if (iterator.hasNext()) {
                    parenthesizeSubtree(child, s);
                    s.append(", ");
                } else {
                    parenthesizeSubtree(child, s);
                    break;
                }
            }
            s.append(")");
        }
    }

    private class ElementIterator implements Iterator<E> {
        Iterator<Position<E>> posIterator = positions().iterator();

        @Override
        public boolean hasNext() {
            return posIterator.hasNext();
        }

        @Override
        public E next() {
            return posIterator.next().getElement();
        }

        @Override
        public void remove() {
            posIterator.remove();
        }
    }
}
