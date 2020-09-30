package ds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class Sort {
    public static <E> void heapSort(ArrayList<E> list) {
        PriorityQueue<E> queue = new PriorityQueue<>(list);
        list.clear();
        while (!queue.isEmpty()) {
            list.add(queue.poll());
        }
    }

    public static <E extends Comparable<? super E>> void selectSort(ArrayList<E> list) {
        for (int x = 0; x < list.size() - 1; x++) {
            int small = x;
            for (int y = x + 1; y < list.size(); y++) {
                if (list.get(y).compareTo(list.get(small)) < 0) {
                    small = y;
                }
            }
            Collections.swap(list, small, x);
        }
    }

    public static <E extends Comparable<? super E>> void insertSort(ArrayList<E> list) {
        for (int x = 1; x < list.size(); x++) {
            E value = list.get(x);
            int y = x - 1;
            for (; y >= 0; y--) {
                if (list.get(y).compareTo(value) <= 0)
                    break;
                list.set(y + 1, list.get(y));
            }
            list.set(y + 1, value);
        }
    }

    public static <E extends Comparable<? super E>> void shellSort(ArrayList<E> list) {
        int times = (int) (Math.log(list.size() + 1) / Math.log(2));
        for (; times != 0; times--) {
            int span = (int) Math.pow(2, times) - 1;
            for (int x = span; x < list.size(); x++) {
                E value = list.get(x);
                int y = x - span;
                for (; y >= 0; y -= span) {
                    if (list.get(y).compareTo(value) <= 0)
                        break;
                    list.set(y + span, list.get(y));
                }
                list.set(y + span, value);
            }
        }
    }

    public static <E extends Comparable<? super E>> void quickSort(ArrayList<E> list) {
        if (list == null || list.size() <= 1) {
            return;
        }
        quickSort(list, 0, list.size() - 1);
    }

    private static <E extends Comparable<? super E>> void quickSort(ArrayList<E> list, int begin, int end) {
        if (end - begin == 0) {
            return;
        }
        int ind = split(list, begin, end);
        quickSort(list, begin, ind - 1);
        quickSort(list, ind, end);
    }

    public static <E extends Comparable<? super E>> void mergeSort(ArrayList<E> list) {
        if (list == null || list.size() <= 1) {
            return;
        }
        mergeSort(list, 0, list.size() - 1);
    }

    private static <E extends Comparable<? super E>> void mergeSort(ArrayList<E> list, int begin, int end) {
        int size = end - begin + 1;
        if (size == 1) {
            return;
        }
        int end1 = begin + size / 2 - 1;
        int begin2 = begin + size / 2;
        mergeSort(list, begin, end1);
        mergeSort(list, begin2, end);
        ArrayList<E> temp = new ArrayList<>(size);
        int ind1 = begin, ind2 = begin2, ind = 0;
        while (ind1 <= end1 && ind2 <= end) {
            if (list.get(ind1).compareTo(list.get(ind2)) <= 0) {
                temp.add(list.get(ind1++));
            } else {
                temp.add(list.get(ind2++));
            }
        }
        while (ind1 <= end1) {
            temp.add(list.get(ind1++));
        }
        while (ind2 <= end) {
            temp.add(list.get(ind2++));
        }
        for (int i = 0; i < size; i++) {
            list.set(i + begin, temp.get(i));
        }
    }

    public static <E extends Comparable<? super E>> E quickSelect(ArrayList<E> list, int kMin) {
        return quickSelect(list, kMin, 0, list.size() - 1);
    }

    private static <E extends Comparable<? super E>> E quickSelect(ArrayList<E> list, int kMin, int begin, int end) {
        if (end - begin == 0) {
            return list.get(begin);
        }
        int ind = split(list, begin, end);
        int sizeLeft = ind - begin;
        if (kMin <= sizeLeft) {
            return quickSelect(list, kMin, begin, ind - 1);
        } else {
            return quickSelect(list, kMin - sizeLeft, ind, end);
        }
    }

    private static <E extends Comparable<? super E>> int split(ArrayList<E> list, int begin, int end) {
        int mid = (begin + end + 1) / 2;
        E pivot = list.get(mid);
        int indLeft = begin - 1, indRight = end + 1;
        while (true) {
            do {
                indLeft++;
            } while (list.get(indLeft).compareTo(pivot) < 0);
            do {
                indRight--;
            } while (list.get(indRight).compareTo(pivot) > 0);
            if (indLeft >= indRight) {
                break;
            }
            Collections.swap(list, indLeft, indRight);
        }
        return indLeft;
    }

    public static void bucketSort(ArrayList<Integer> list) {
        int max = Collections.max(list);
        int min = Collections.min(list);
        int size = max - min + 1;
        ArrayList<Integer> count = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            count.add(0);
        }
        for (Integer i : list) {
            count.set(i - min, count.get(i - min) + 1);
        }
        int cur = 0;
        for (int x = 0; x < count.size(); x++) {
            for (int y = 0; y < count.get(x); y++) {
                list.set(cur++, x + min);
            }
        }
    }

    public static void radixSort(ArrayList<Integer> list) {
        ArrayList<ArrayList<Integer>> buckets = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            buckets.add(new ArrayList<>());
        }
        int maxNum = Collections.max(list);
        int maxDigit = 1;
        while ((maxNum /= 10) != 0) {
            maxDigit++;
        }
        int maxDivide = (int) Math.pow(10, maxDigit - 1);
        for (int i = 1; i <= maxDivide; i *= 10) {
            for (Integer number : list) {
                buckets.get((number / i) % 10).add(number);
            }
            list.clear();
            for (ArrayList<Integer> bucket : buckets) {
                list.addAll(bucket);
                bucket.clear();
            }
        }
    }

    public static void bubbleSort(ArrayList<Integer> list) {
        for (int x = 0; x < list.size() - 1; x++) {
            for (int y = 0; y < list.size() - 1; y++) {
                if (list.get(y).compareTo(list.get(y + 1)) > 0) {
                    Collections.swap(list, y, y + 1);
                }
            }
        }
    }
}
