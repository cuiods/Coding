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
        BSTNode<T> p = root, prev = null;
        while (p != null) {
            prev = p;
            if (p.data.compareTo(data) < 0) {
                p = p.right;
            } else {
                p = p.left;
            }
        }
        if (root == null) {
            root = new BSTNode<T>(data);
        } else if (prev.data.compareTo(data) < 0) {
            prev.right = new BSTNode<T>(data);
        } else {
            prev.left = new BSTNode<T>(data);
        }
    }

    public void inorder() {
        inorder(root);
    }

    protected void inorder(BSTNode<T> node) {
        if (node != null) {
            inorder(node.left);
            visit(node.data);
            inorder(node.right);
        }
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

    protected void visit(T node) {
        System.out.println(node.toString() + " ");
    }
}
