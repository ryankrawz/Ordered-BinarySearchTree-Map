// Immutable BST-Based Ordered Map ADT
// Ryan Krawczyk

import java.util.NoSuchElementException;
import java.util.List;
import java.util.ArrayList;

public class MapC<K extends Comparable<K>, V> implements Map<K, V> {

    private class Node {
        private K key;
        private V val;
        private Node left, right;

        Node(K key, V val, Node left, Node right) {
            this.key = key;
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    private Node root;
    private int size;

    public MapC() {
        this.root = null;
        this.size = 0;
    }

    public V get(K key) {
        checkEmpty();
        return getHelper(root, key);
    }

    // Parses tree using compareTo until matching key is located, returns null if key does not exist
    private V getHelper(Node node, K key) {
        if (node == null) return null;
        else {
            int comp = key.compareTo(node.key);
            if (comp == 0)          return node.val;
            else if (comp > 0)      return getHelper(node.right, key);
            else                    return getHelper(node.left, key);
        }
    }

    public Map<K, V> put(K key, V val) {
        size++;
        if (root == null)       root = new Node(key, val, null, null);
        else                    putHelper(root, key, val);
        return this;
    }

    // Parses tree using compareTo until available spot (indicated by null) is located
    private void putHelper(Node node, K key, V val) {
        int comp = key.compareTo(node.key);
        if (comp == 0) node.val = val;
        else if (comp > 0) {
            if (node.right == null)     node.right = new Node(key, val, null, null);
            else                        putHelper(node.right, key, val);
        } else {
            if (node.left == null)      node.left = new Node(key, val, null, null);
            else                        putHelper(node.left, key, val);
        }
    }

    public boolean isEmpty() { return size == 0; }

    // Throws exception for operations requiring the tree to contain items
    private void checkEmpty() { if (isEmpty()) throw new NoSuchElementException("EMPTY MAP"); }

    public int size() { return size; }

    public boolean contains(K key) {
        checkEmpty();
        return containHelper(root, key);
    }

    // Parses tree using compareTo, returns false if entire branch has been visited
    private boolean containHelper(Node node, K key) {
        if (node == null) return false;
        else {
            int comp = key.compareTo(node.key);
            if (comp == 0)          return true;
            else if (comp > 0)      return containHelper(node.right, key);
            else                    return containHelper(node.left, key);
        }
    }

    public K min() {
        checkEmpty();
        return minHelper(root);
    }

    // Recursively walks left to locate min (employs relational invariant)
    private K minHelper(Node node) {
        if (node.left == null)      return node.key;
        else                        return minHelper(node.left);
    }

    public String toString() {
        checkEmpty();
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n***\n");
        stringHelper(root, sb);
        sb.append("***\n\n");
        return sb.toString();
    }

    // In-order traversal of tree, appends key and value
    private void stringHelper(Node node, StringBuilder sb) {
        if (isLeaf(node)) sb.append(node.key).append(" --> ").append(node.val).append("\n");
        else {
            if (node.left != null) stringHelper(node.left, sb);
            sb.append(node.key).append(" --> ").append(node.val).append("\n");
            if (node.right != null) stringHelper(node.right, sb);
        }
    }

    public K floor(K key) {
        checkEmpty();
        return floorHelper(root, key);
    }

    // Parses tree using compareTo, calls successor on input key
    private K floorHelper(Node node, K key) {
        if (node == null) return null;
        else {
            int comp = key.compareTo(node.key);
            if (comp == 0)          return successor(node).key;
            else if (comp > 0)      return floorHelper(node.right, key);
            else                    return floorHelper(node.left, key);
        }
    }

    // Returns the maximum key of the left field of the input node
    private Node successor(Node node) {
        Node s = node.left == null ? null : max(node.left);
        return s;
    }

    // Recursively walks right to locate max (employs relational invariant)
    private Node max(Node node) {
        if (node.right == null)     return node;
        else                        return max(node.right);
    }

    // Retrieves index n - 1 from list of keys
    public K select(int n) {
        checkEmpty();
        List<K> l = new ArrayList<K>();
        indexHelper(root, l);
        return l.get(n - 1);
    }

    // Outputs rank based on key's location in list of keys
    public int rank(K key) {
        checkEmpty();
        List<K> l = new ArrayList<K>();
        indexHelper(root, l);
        return l.indexOf(key) + 1;
    }

    // Generates list of keys using in-order traversal
    private void indexHelper(Node node, List<K> l) {
        if (isLeaf(node)) l.add(node.key);
        else {
            if (node.left != null) indexHelper(node.left, l);
            l.add(node.key);
            if (node.right != null) indexHelper(node.right, l);
        }
    }

    public Map<K, V> deleteMin() {
        checkEmpty();
        if (root.left == null)      root = null;
        else                        deleteMinHelper(root.left, root);
        return this;
    }

    // Locates minimum with recursive walk left, resets trailing pointer to null
    private void deleteMinHelper(Node node1, Node node2) {
        if (node1.left == null)     node2.left = node1.right;
        else                        deleteMinHelper(node1.left, node2.left);
    }

    public Map<K, V> delete(K key) {
        checkEmpty();
        int comp = key.compareTo(root.key);
        boolean check = (comp > 0 && root.right == null) || (comp < 0 && root.left == null);
        if (!check) {
            if (comp > 0)           deleteHelper(root.right, root, key);
            else if (comp < 0)      deleteHelper(root.left, root, key);
            else                    root = null;
        }
        return this;
    }

    // Locates input key using compareTo, reconnects trailing pointer to nodes following input key
    private void deleteHelper(Node node1, Node node2, K key) {
        int comp1 = key.compareTo(node1.key);
        int comp2 = key.compareTo(node2.key);
        if (comp1 == 0) {
            Node s = successor(node1);
            if (s != null) {
                s.right = node1.right;
                s.left = node1.left;
            }
            if (comp2 > 0) {
                if (s == null) node2.right = node1.right;
                else {
                    eliminateSuccessor(node2.right.left, node2.right, 0);
                    node2.right = s;
                }
            } else {
                if (s == null) node2.left = node1.right;
                else {
                    eliminateSuccessor(node2.left.left, node2.left, 0);
                    node2.left = s;
                }
            }
            return;
        }
        else if (comp1 > 0 && node1.right != null)      deleteHelper(node1.right, node1, key);
        else if (comp1 < 0 && node1.left != null)       deleteHelper(node1.left, node1, key);
        else                                            return;
    }

    // Sets pointers to successor of node in deleteHelper equal to null once successor is in place of deleted key
    private void eliminateSuccessor(Node node1, Node node2, int c) {
        if (node1.right == null && c == 0) node2.left = node1.left;
        else {
            if (node1.right == null)        node2.right = node1.right;
            else                            eliminateSuccessor(node1.right, node1, c + 1);
        }
    }

    private boolean isLeaf(Node node) { return node.left == null && node.right == null; }

    public static void main(String[] args) {
        /* Unit Testing */
        Map<Integer, String> m1 = new MapC<Integer, String>();
        Map<String, Integer> m2 = new MapC<String, Integer>();

        // put(K key, V val)
        Map<Integer, String> testMapInt;
        Map<String, Integer> testMapString;
        testMapInt = m1.put(3, "C").put(2, "B").put(5, "E").put(4, "D").put(1, "A").put(7, "G").put(6, "F");
        testMapString = m2.put("C", 3).put("A", 1).put("G", 7).put("B", 2).put("F", 6).put("D", 4).put("E", 5);
        System.out.format("%ntestMapInt after put:%s", testMapInt.toString());
        System.out.format("testMapString after put:%s", testMapString.toString());

        // get(K key)
        System.out.format("Getting key 4 from testMapInt: %s", testMapInt.get(4));
        System.out.format("%nGetting key B from testMapString: %d%n%n", testMapString.get("B"));

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
        System.out.format("After deleting key 6 from testMapInt:%s", testMapInt.delete(6).toString());
        System.out.format("After deleting key F from testMapString:%s", testMapString.delete("F").toString());
    }

}
