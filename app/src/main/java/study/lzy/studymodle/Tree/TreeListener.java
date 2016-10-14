package study.lzy.studymodle.Tree;// @author: lzy  time: 2016/09/14.

public interface TreeListener{
    void noChild(Tree tree);
    void showChild(Tree tree);
    void dismissChild(Tree tree);
    Tree FindParent(Object data);
    String getTitle(Object data);

}