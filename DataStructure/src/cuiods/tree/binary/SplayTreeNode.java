package cuiods.tree.binary;

/**
 * node of splay tree
 * @author cuiods
 */
public class SplayTreeNode<T extends Comparable<? super T>> extends BSTNode<T> {

    public SplayTreeNode() {
        right = left = null;
    }
    public SplayTreeNode(T data) {
        this(data,null,null);
    }
    public SplayTreeNode(T data, SplayTreeNode<T> left, SplayTreeNode<T> right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }
}
