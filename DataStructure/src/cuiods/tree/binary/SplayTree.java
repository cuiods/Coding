package cuiods.tree.binary;

/**
 * splay tree implement
 * http://www.cnblogs.com/skywang12345/p/3604286.html
 * @author cuiods
 */
public abstract class SplayTree<T extends Comparable<? super T>> extends BSTree<T> {

    /**
     * 处理插入时遇到相同的节点
     * @param node 插入时已经存在的节点
     */
    protected abstract void handleSame(SplayTreeNode<T> node);

    @Override
    public void insert(T key) {
        SplayTreeNode<T> z=new SplayTreeNode<T>(key);

        // 插入节点
        root = insert((SplayTreeNode<T>) root, z);
        // 将节点(key)旋转为根节点
        root = splay((SplayTreeNode<T>) root, key);
    }

    @Override
    public T search(T key) {
        T result = super.search(key);
        splay(key);
        return result;
    }

    private void splay(T key) {
        root = splay((SplayTreeNode<T>) root, key);
    }


    /*
    * 旋转key对应的节点为根节点，并返回根节点。
    *
    * 注意：
    *   (a)：伸展树中存在"键值为key的节点"。
    *          将"键值为key的节点"旋转为根节点。
    *   (b)：伸展树中不存在"键值为key的节点"，并且key < tree.key。
    *      b-1 "键值为key的节点"的前驱节点存在的话，将"键值为key的节点"的前驱节点旋转为根节点。
    *      b-2 "键值为key的节点"的前驱节点不存在的话，则意味着，key比树中任何键值都小，那么此时，将最小节点旋转为根节点。
    *   (c)：伸展树中不存在"键值为key的节点"，并且key > tree.key。
    *      c-1 "键值为key的节点"的后继节点存在的话，将"键值为key的节点"的后继节点旋转为根节点。
    *      c-2 "键值为key的节点"的后继节点不存在的话，则意味着，key比树中任何键值都大，那么此时，将最大节点旋转为根节点。
    */
    private SplayTreeNode<T> splay(SplayTreeNode<T> tree, T data) {
        if (tree == null)
            return null;

        SplayTreeNode<T> N = new SplayTreeNode<T>();
        SplayTreeNode<T> l = N;
        SplayTreeNode<T> r = N;
        SplayTreeNode<T> c;

        for (;;) {
            int cmp = data.compareTo(tree.data);
            if (cmp < 0) {
                if (tree.left == null)
                    break;
                if (data.compareTo(tree.left.data) < 0) {
                    c = (SplayTreeNode<T>) tree.left;                           /* rotate right */
                    tree.left = c.right;
                    c.right = tree;
                    tree = c;
                    if (tree.left == null)
                        break;
                }
                r.left = tree;                               /* link right */
                r = tree;
                tree = (SplayTreeNode<T>) tree.left;
            } else if (cmp > 0) {
                if (tree.right == null)
                    break;
                if (data.compareTo(tree.right.data) > 0) {
                    c = (SplayTreeNode<T>) tree.right;                          /* rotate left */
                    tree.right = c.left;
                    c.left = tree;
                    tree = c;
                    if (tree.right == null)
                        break;
                }
                l.right = tree;                              /* link left */
                l = tree;
                tree = (SplayTreeNode<T>) tree.right;
            } else {
                break;
            }
        }
        l.right = tree.left;                                /* assemble */
        r.left = tree.right;
        tree.left = N.right;
        tree.right = N.left;
        return tree;
    }

    /*
    * 将结点插入到伸展树中，并返回根节点
    *
    * 参数说明：
    *     tree 伸展树的
    *     z 插入的结点
    */
    private SplayTreeNode<T> insert(SplayTreeNode<T> tree, SplayTreeNode<T> z) {
        int cmp;
        SplayTreeNode<T> y = null;
        SplayTreeNode<T> x = tree;

        // 查找z的插入位置
        while (x != null) {
            y = x;
            cmp = z.data.compareTo(x.data);
            if (cmp < 0)
                x = (SplayTreeNode<T>) x.left;
            else if (cmp > 0)
                x = (SplayTreeNode<T>) x.right;
            else {
                System.out.printf("不允许插入相同节点(%d)!\n", z.data);
                z=null;
                return tree;
            }
        }

        if (y==null)
            tree = z;
        else {
            cmp = z.data.compareTo(y.data);
            if (cmp < 0)
                y.left = z;
            else
                y.right = z;
        }

        return tree;
    }


}
