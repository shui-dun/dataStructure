package algorithm;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;


/**
 * 使用回溯算法求解收费公路重建问题
 * 即根据一维坐标下多个点之间距离集合求出这些点的坐标集合
 */
public class Turnpike {
    private TreeMultiset<Integer> distances = TreeMultiset.create();

    private Integer[] points;

    private boolean success;

    private boolean verifyDis(int x, int begin, int end) {
        Multiset<Integer> disFromX = HashMultiset.create();
        for (int i = 0; i < begin; i++) {
            disFromX.add(Math.abs(x - points[i]));
        }
        for (int i = end + 1; i < points.length; i++) {
            disFromX.add(Math.abs(x - points[i]));
        }
        for (Multiset.Entry<Integer> entry : disFromX.entrySet()) {
            if (distances.count(entry.getElement()) < entry.getCount()) {
                return false;
            }
        }
        return true;
    }

    private void removeDis(int x, int begin, int end) {
        for (int i = 0; i < begin; i++) {
            distances.remove(Math.abs(points[i] - x));
        }
        for (int i = end + 1; i < points.length; i++) {
            distances.remove(Math.abs(points[i] - x));
        }
    }

    private void addDis(int x, int begin, int end) {
        for (int i = 0; i < begin; i++) {
            distances.add(Math.abs(points[i] - x));
        }
        for (int i = end + 1; i < points.length; i++) {
            distances.add(Math.abs(points[i] - x));
        }
    }

    private boolean solve(int begin, int end) {
        if (distances.isEmpty()) {
            return true;
        }
        int max = distances.lastEntry().getElement();
        int newX = max;
        if (verifyDis(newX, begin, end)) {
            removeDis(newX, begin, end);
            points[end] = newX;
            if (solve(begin, end - 1)) {
                return true;
            } else {
                addDis(newX, begin, end);
            }
        }
        newX = points[points.length - 1] - max;
        if (verifyDis(newX, begin, end)) {
            removeDis(newX, begin, end);
            points[begin] = newX;
            if (solve(begin + 1, end)) {
                return true;
            } else {
                addDis(newX, begin, end);
            }
        }
        return false;
    }

    public Turnpike(int[] distances) {
        double temp = (1 + Math.sqrt(8 * distances.length + 1)) / 2;
        int n = (int) temp;
        if (n != temp) {
            throw new IllegalArgumentException();
        }
        for (int i : distances) {
            this.distances.add(i);
        }
        points = new Integer[n];
        points[0] = 0;
        points[n - 1] = this.distances.lastEntry().getElement();
        this.distances.remove(points[n - 1]);
        success = solve(1, n - 2);
    }

    public Integer[] getPoints() {
        if (success) {
            return points;
        } else {
            return null;
        }
    }
}
