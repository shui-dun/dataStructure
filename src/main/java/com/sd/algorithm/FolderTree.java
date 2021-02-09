package com.sd.algorithm;

import javafx.util.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件树
 */
public class FolderTree {
    private String path;

    public FolderTree(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void traverse() {
        traverse(new File(path), 0, new ArrayList<Pair<Integer, Boolean>>());
    }

    private void traverse(File parent, int depth, List<Pair<Integer, Boolean>> list) {
        if (depth != 0) {
            for (int i = 0; i < depth - 1; i++) {
                if (list.get(i).getValue()) {
                    System.out.print("     ");
                } else {
                    System.out.print("|    ");
                }
            }
            if (list.get(list.size() - 1).getValue()) {
                System.out.print("+----");
            } else {
                System.out.print("|----");
            }
            for (int i = 0; i < depth - 1; i++) {
                System.out.print(list.get(i).getKey().toString() + '.');
            }
            System.out.print(list.get(list.size() - 1).getKey().toString() + "  ");
        }
        System.out.println(parent.getName());
        File[] children = parent.listFiles();
        if (children != null && children.length != 0) {
            for (int i = 0; i < children.length - 1; i++) {
                list.add(new Pair<>(i, false));
                traverse(children[i], depth + 1, list);
            }
            list.add(new Pair<>(children.length - 1, true));
            traverse(children[children.length - 1], depth + 1, list);
        }
        if (!list.isEmpty()) {
            list.remove(list.size() - 1);
        }
    }
}
