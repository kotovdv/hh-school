package com.kotovdv.hhschool.tropical.island.model.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitriy Kotov
 */
public class IslandTree {

    private Node root;

    public IslandTree(IslandTreeItem rootData) {
        root = new Node();
        root.data = rootData;
        root.children = new ArrayList<>();
    }

    private class Node {
        private IslandTreeItem data;
        private Node parent;
        private List<Node> children;
    }
}
