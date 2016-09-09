package cuiods.tree.binary;

/**
 * Binary Search Tree
 * @author cuiods
 */
public class BSTree<T extends Comparable<? super T>> {
    protected BSTNode<T> root;

    public T search(T data) {
        return search(root, data);
    }

    public void insert(T data) {

    }

    protected T search(BSTNode<T> p, T data) {
        while (p != null) {
            if (data.equals(p.data)) {
                return data;
            } else if (data.compareTo(p.data) < 0) {
                p = p.left;
            } else {
                p = p.right;
            }
        }
        return null;
    }
}
