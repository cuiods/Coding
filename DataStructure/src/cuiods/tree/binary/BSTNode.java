package cuiods.tree.binary;

/**
 * Node of Binary Search Tree
 * @author cuiods
 */
public class BSTNode<T extends Comparable<? super T>> {
    protected T data;
    protected BSTNode<T> left, right;

    public BSTNode() {
        this(null, null, null);
    }

    public BSTNode(T data) {
        this(data, null, null);
    }

    public BSTNode(T data, BSTNode left, BSTNode right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }
}
