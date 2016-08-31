package cuiods.list.skiplist;

/**
 * 跳转表节点
 * @author cuiods
 */
public class SkipListNode<T extends Comparable<? super T>> {
    private T data;
    /**
     * 用数组保存其他节点的引用
     */
    private SkipListNode[] next;

    /**
     * @param data
     *      节点保存的数据
     * @param maxLevel
     *      若跳转表最多保存n个数据，则最多的层次为[log2(n)]
     */
    public SkipListNode(T data, int maxLevel) {
        this.data = data;
        next = new SkipListNode[maxLevel];
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public SkipListNode[] getNext() {
        return next;
    }

    public SkipListNode getNext(int index) {
        return next[index];
    }

    public void setNext(SkipListNode[] next) {
        this.next = next;
    }

    public void setNext(SkipListNode next, int i) {
        this.next[i] = next;
    }
}
