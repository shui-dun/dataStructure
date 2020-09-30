package ds;

import java.util.Arrays;

public class IntDisjointSet implements DataStructure {
    private int[] parent;

    public IntDisjointSet(int size) {
        parent = new int[size];
        Arrays.fill(parent, -1);
    }

    public boolean inSameSet(int a, int b) {
        return find(a) == find(b);
    }

    public int find(int pos) {
        if (parent[pos] < 0) {
            return pos;
        }
        int root = find(parent[pos]);
        parent[pos] = root;
        return root;
    }

    public boolean union(int a, int b) {
        if (inSameSet(a, b)) {
            return false;
        }
        int rootA = find(a), rootB = find(b);
        int sizeA = -parent[rootA], sizeB = -parent[rootB];
        if (sizeA < sizeB) {
            parent[rootA] = rootB;
            parent[rootB] = -(sizeA + sizeB);
        } else {
            parent[rootB] = rootA;
            parent[rootA] = -(sizeA + sizeB);
        }
        return true;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return parent.length;
    }
}
