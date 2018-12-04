// Immutable Ordered Map ADT
// Ryan Krawczyk

public class MapC<K extends Comparable<K>, V> implements Map<K, V> {

    private Map<K, V> tree, next;

    /* MapC wraps the mutable binary search trees and outputs an updated tree in the next field while
       retaining the structure of the old tree in the tree field */
    public MapC() {
        this.tree = new BST(null, 0);
        this.next = new BST(null, 0);
    }

    public V get(K key) { return tree.get(key); }

    public Map<K, V> put(K key, V val) {
        tree = new BST(next.root(), next.size());
        next = new BST(next.put(key, val).root(), next.size());
        return this;
    }

    public boolean isEmpty() { return tree.isEmpty(); }

    public int size() { return tree.size(); }

    public Node root() { return tree.root(); }

    public boolean contains(K key) { return tree.contains(key); }

    public K min() { return tree.min(); }

    public String toString() { return tree.toString(); }

    public K floor(K key) { return tree.floor(key); }

    public K select(int n) { return tree.select(n); }

    public int rank(K key) { return tree.rank(key); }

    public Map<K, V> deleteMin() {
        tree = new BST(next.root(), next.size());
        next = new BST(next.deleteMin().root(), next.size());
        return this;
    }

    public Map<K, V> delete(K key) {
        tree = new BST(next.root(), next.size());
        next = new BST(next.delete(key).root(), next.size());
        return this;
    }

    public static void main(String[] args) {
        /* Unit Testing */
        Map<Integer, String> testMapInt = new MapC<Integer, String>();
        Map<String, Integer> testMapString = new MapC<String, Integer>();

        // put(K key, V val)
        testMapInt = testMapInt.put(3, "C").put(2, "B").put(5, "E").put(4, "D").put(1, "A").put(7, "G").put(6, "F");
        testMapString = testMapString.put("C", 3).put("A", 1).put("G", 7).put("B", 2).put("F", 6).put("D", 4).put("E", 5);
        System.out.format("%ntestMapInt after put:%s", testMapInt.toString());
        System.out.format("testMapString after put:%s", testMapString.toString());

        // get(K key)
        System.out.format("Getting key 4 from testMapInt: %s", testMapInt.get(4));
        System.out.format("%nGetting key D from testMapString: %d%n%n", testMapString.get("D"));

        // contains(K key)
        System.out.format("testMapInt contains 1: %b", testMapInt.contains(1));
        System.out.format("%ntestMapInt contains 8: %b", testMapInt.contains(8));
        System.out.format("%ntestMapString contains A: %b", testMapString.contains("A"));
        System.out.format("%ntestMapString contains H: %b%n%n", testMapString.contains("H"));

        // min()
        System.out.format("Min of testMapInt: %d", testMapInt.min());
        System.out.format("%nMin of testMapString: %s%n%n", testMapString.min());

        // floor(K key)
        System.out.format("Floor of 7 in testMapInt: %d", testMapInt.floor(7));
        System.out.format("%nFloor of G in testMapString: %s%n%n", testMapString.floor("G"));

        // select(int n)
        System.out.format("Selecting rank 5 from testMapInt: %d", testMapInt.select(5));
        System.out.format("%nSelecting rank 5 from testMapString: %s%n%n", testMapString.select(5));

        // rank(K key)
        System.out.format("Rank of key 3 from testMapInt: %d", testMapInt.rank(3));
        System.out.format("%nRank of key C from testMapString: %s%n%n", testMapString.rank("C"));

        // deleteMin()
        System.out.format("After deleting min of testMapInt:%s", testMapInt.deleteMin().toString());
        System.out.format("After deleting min of testMapString:%s", testMapString.deleteMin().toString());

        // delete(K key)
        System.out.format("After deleting key 6 from testMapInt:%s", testMapInt.put(1, "A").delete(6).toString());
        System.out.format("After deleting key F from testMapString:%s", testMapString.put("A", 1).delete("F").toString());
    }

}
