package com.sd.algorithm;


import java.util.LinkedList;
import java.util.List;

/**
 * 零一背包问题
 */
public class Backpack {
    public static class Item {
        private int weight;

        private int value;

        public int getWeight() {
            return weight;
        }

        public int getValue() {
            return value;
        }

        public Item(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }
    }

    private Item[] items;

    private int capacity;

    public Backpack(int[] weight, int[] value, int capacity) {
        if (weight.length != value.length) {
            throw new IllegalArgumentException();
        }
        items = new Item[weight.length];
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item(weight[i], value[i]);
        }
        this.capacity = capacity;
    }

    public int maxValue() {
        int[] earn = new int[capacity + 1];
        for (int i = 0; i < items[0].weight; i++) {
            earn[i] = 0;
        }
        for (int i = items[0].weight; i <= capacity; i++) {
            earn[i] = items[0].value;
        }
        for (int k = 1; k < items.length; k++) {
            for (int c = capacity; c >= items[k].weight; c--) {
                int val1 = earn[c];
                int val2 = earn[c - items[k].weight] + items[k].value;
                earn[c] = Math.max(val1, val2);
            }
        }
        return earn[capacity];
    }

    public List<Item> maxValueWithItems() {
        List<Item> list = new LinkedList<>();
        int[][] earn = new int[items.length][capacity + 1];
        for (int i = 0; i < items[0].weight; i++) {
            earn[0][i] = 0;
        }
        for (int i = items[0].weight; i <= capacity; i++) {
            earn[0][i] = items[0].value;
        }
        for (int k = 1; k < items.length; k++) {
            for (int c = items[k].weight; c <= capacity; c++) {
                int val1 = earn[k - 1][c];
                int val2 = earn[k - 1][c - items[k].weight] + items[k].value;
                earn[k][c] = Math.max(val1, val2);
            }
        }
        int curWeight = capacity;
        for (int i = items.length - 1; i > 0; i--) {
            if (earn[i][curWeight] != earn[i - 1][curWeight]) {
                list.add(items[i]);
                curWeight -= items[i].weight;
            }
        }
        if (earn[0][curWeight] != 0) {
            list.add(items[0]);
        }
        return list;
    }

    public Item[] getItems() {
        return items;
    }

    public int getCapacity() {
        return capacity;
    }
}
