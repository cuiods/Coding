package cuiods.list.skiplist;


import java.util.Random;

public class SkipList<T extends Comparable<? super T>> {

    private int maxLevel;
    private int[] power;
    private SkipListNode[] rootNodes;
    private Random random = new Random();

    public SkipList(int maxLevel) {
        assert maxLevel >= 1;
        this.maxLevel = maxLevel;
        rootNodes = new SkipListNode[maxLevel];
        power = new int[maxLevel];
        initialPower();
    }

    public SkipListNode find(T key) {
        int level = maxLevel - 1;
        SkipListNode<T> previous, current;
        for (; level >= 0 && rootNodes[level] == null; level--);
        previous = current = rootNodes[level];
        while (true) {
            if (key.equals(current.getData())) {
                return current;
            } else if (key.compareTo(current.getData()) < 0) {
                if (level == 0) return null;
                else if (current == rootNodes[level]) current = rootNodes[--level];
                else current = previous.getNext(--level);
            } else {
                previous = current;
                if (current.getNext(level) != null) {
                    current = current.getNext(level);
                } else {
                    for (level--; level >= 0 && current.getNext(level)==null; level--);
                    if (level >= 0)
                        current = current.getNext(level);
                    else return null;
                }
            }
        }
    }

    public void insert(T key) {
        SkipListNode<T>[] current = new SkipListNode[maxLevel];
        SkipListNode<T>[] previous = new SkipListNode[maxLevel];
        SkipListNode<T> newNode = null;
        int level;
        current[maxLevel-1] = rootNodes[maxLevel-1];
        previous[maxLevel-1] = null;
        for (level = maxLevel - 1; level >= 0; level--) {
            while (current[level] != null && current[level].getData().compareTo(key) < 0) {
                previous[level] = current[level];
                current[level] = current[level].getNext(level);
            }
            if (current[level] != null && current[level].getData().equals(key)) return;
            if (level > 0) {
                if (previous[level] == null) {
                    current[level - 1] = rootNodes[level - 1];
                    previous[level - 1] = null;
                } else {
                    current[level - 1] = previous[level].getNext(level - 1);
                    previous[level - 1] = previous[level];
                }
            }
        }
        level = chooseLevel();

        newNode = new SkipListNode<T>(key, level + 1);
        for (int i = 0; i < level; i++) {
            newNode.setNext(current[i],i);
            if (previous[i] == null) {
                rootNodes[i] = newNode;
            } else {
                previous[i].setNext(newNode, i);
            }
        }
    }

    private void initialPower() {
        power[maxLevel-1] = (2 << (maxLevel-1)) - 1;
        for (int i = maxLevel - 2, j = 0; i >= 0; i--,j++) {
            power[i] = power[i+1] - (2 << j);
        }
    }

    private int chooseLevel() {
        int i, r = Math.abs(random.nextInt()) % power[maxLevel-1] + 1;
        for (i = 1; i < maxLevel; i++) {
            if (r < power[i]) {
                return i - 1;
            }
        }
        return i - 1;
    }

}
