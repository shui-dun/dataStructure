package algorithm;

import java.security.InvalidParameterException;
import java.util.*;

/**
 * best fit decrease bin pack algorithm
 */
public class BFDBinPack {
    private int CAPACITY;

    public class Bin {
        private LinkedList<Integer> items = new LinkedList<>();
        private int remain = CAPACITY;

        public int getRemain() {
            return remain;
        }

        public List<Integer> getItems() {
            return items;
        }

        public boolean add(int val) {
            if (val > remain) {
                return false;
            } else {
                items.addLast(val);
                remain -= val;
                return true;
            }
        }
    }

    private List<Bin> bins = new LinkedList<>();

    public BFDBinPack(Collection<Integer> values,int capacity) {
        CAPACITY=capacity;
        List<Integer> list = new ArrayList<>(values);
        list.sort((o1, o2) -> Integer.compare(o2, o1));
        Queue<Bin> queue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.remain, o1.remain));
        for (int val : list) {
            if (val < 0 || val > CAPACITY) {
                throw new InvalidParameterException(String.format("所有值必须介于0与%d之间", CAPACITY));
            }
            Bin bin = queue.peek();
            if (bin == null || bin.remain < val) {
                Bin newBin = new Bin();
                newBin.add(val);
                queue.offer(newBin);
                bins.add(newBin);
            } else {
                queue.poll();
                bin.add(val);
                queue.offer(bin);
            }
        }
    }

    public List<Bin> getBins() {
        return bins;
    }
}
