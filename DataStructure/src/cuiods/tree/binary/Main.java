package cuiods.tree.binary;

/**
 * @author cuiods
 */
public class Main {
    public static void main(String[] args) {
        BSTree<Integer> tree = new BSTree<>();
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        System.out.println(tree.search(2));
    }
}
