package study.lzy.studymodle.Tree;
// @author: lzy  time: 2016/09/14.

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tree {
    public static final int NO_PARENT = -1;
    public static final int FIRST_LEVEL = 0;
    private static int i = 0;
    private int node;
    private int parentNode = NO_PARENT;
    private int level = FIRST_LEVEL;
    private boolean hasChild = false;
    private boolean childShow = false;
    private Object data = null;
    private String title = "";
    private TreeListener method = null;


    private Tree(int node, int parentNode, int level, Object data, String title, TreeListener method) {
        this.node = node;
        this.parentNode = parentNode;
        this.level = level;
        this.data = data;
        this.title = title;
        this.method = method;

    }

    public static Tree getInstance(Object data, String title, TreeListener method) {
        int parentNode = NO_PARENT;
        int node = i;
        int level = FIRST_LEVEL;
        i++;
        if (method != null) {
            Tree tree = method.FindParent(data);
            if (tree != null) {
                parentNode = tree.node;
                level = tree.level + 1;
            }
        }
        return new Tree(node, parentNode, level, data, title, method);
    }

    public static Tree getInstance(Object data, TreeListener method) {
        int parentNode = NO_PARENT;
        int node = i;
        int level = FIRST_LEVEL;
        String title = "ERROR";
        i++;
        if (method != null) {
            Tree tree = method.FindParent(data);
            if (tree != null) {
                parentNode = tree.node;
                level = tree.level + 1;
            }
            title = method.getTitle(data);
        }
        return new Tree(node, parentNode, level, data, title, method);
    }

    public List<Tree> getChild(Map<Integer, Tree> treeMap) {
        List<Tree> list = new ArrayList<>();
        for (Map.Entry<Integer, Tree> entry : treeMap.entrySet()) {
            if (entry.getValue().getParentNode() == node) {
                list.add(entry.getValue());
            }

        }
        return list;
    }

    public static List<Tree> getChild(int node, Map<Integer, Tree> treeMap) {
        List<Tree> list = new ArrayList<>();
        Tree tree = treeMap.get(node);
        Map<Integer, Tree> childMap=new HashMap<>();
        childMap.putAll(treeMap);
        if (tree == null) {
            for (Map.Entry<Integer, Tree> entry : treeMap.entrySet()) {
                if (entry.getValue().getParentNode() == node) {
                    list.add(entry.getValue());
                    childMap.remove(entry.getValue());
                    list.addAll(getChild(entry.getKey(), childMap));
                }
            }
        } else if (tree.hasChild && tree.childShow) {
            for (Map.Entry<Integer, Tree> entry : treeMap.entrySet()) {
                if (entry.getValue().getParentNode() == node) {
                    list.add(entry.getValue());
                    childMap.remove(entry.getValue());
                    list.addAll(getChild(entry.getKey(), childMap));
                }
            }
        }
        return list;
    }

    public int getLevel() {
        return level;
    }

    public Map<Integer, Tree> saveToMap(Map<Integer, Tree> treeMap) {
        if (treeMap == null || treeMap.isEmpty()) {
            treeMap = new HashMap<>();
        }
        if (!treeMap.containsKey(node)) {
            treeMap.put(node, this);
            Tree parent = treeMap.get(parentNode);
            if (parent!=null)
                parent.hasChild = true;
        }

        return treeMap;
    }

    public Object getData() {
        return data;
    }

    public String getTitle() {
        return title;
    }

    public int getNode() {
        return node;
    }

    public int getParentNode() {
        return parentNode;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public boolean isChildShow() {
        return childShow;
    }

    public void deal() {
        if (method == null) {
            return;
        }
        if (hasChild) {
            childShow = !childShow;
            if (!childShow) {
                method.dismissChild(this);
            } else {
                method.showChild(this);
            }
        } else
            method.noChild(this);
    }


}