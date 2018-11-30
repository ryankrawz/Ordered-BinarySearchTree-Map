// Immutable BST-Based Ordered Map ADT
// Ryan Krawczyk

import java.util.NoSuchElementException;

public class MapC implements Map<K extends Comparable<K>, V> {

    private K key;
    private V val;
    private Node left, right;

    private Node(K key, V val, Node left, Node right) {
        this.key = key;
        this.val = val;
        this.left = left;
        this.right = right;
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

    private V getHelper(Node node, K key) {
        int comp = key.compareTo(node.key);
        if (comp == 0)                                  return node.val;
        else if (comp > 0 && node.right != null)        return getHelper(node.right, key);
        else if (comp < 0 && node.left != null)         return getHelper(node.left, key);
        else                                            return null;
    }

    public Map<K, V> put(K key, V val) {
        size++;
        return putHelper(root, key, val);
    }

    private Map<K, V> putHelper(Node node, K key, V val) {
        int comp = key.compareTo(node.key);
        if (node == null)       node = new Node(key, val, null, null);
        else if (comp == 0)     node.val = val;
        else if (comp > 0)      return putHelper(node.right, key, val);
        else                    return putHelper(node.left, key, val);
        return this;
    }

    public boolean isEmpty() { return size == 0; }

    private void checkEmpty() { if (isEmpty()) throw new NoSuchElementException("EMPTY MAP"); }

    public int size() { return size; }

    public boolean contains(K key) {
        checkEmpty();
        return containHelper(root, key);
    }

    private boolean containHelper(Node node, K key) {
        int comp = key.compareTo(node.key);
        if (node == null)       return false;
        else if (comp == 0)     return true;
        else if (comp > 0)      return containHelper(node.right, key);
        else                    return containHelper(node.left, key);
    }

    public K min() {
        checkEmpty();
        return minHelper(root);
    }

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

    private void stringHelper(Node node, Stringbuilder sb) {
        if (isLeaf(node)) sb.append(node.key).append("\n");
        else {
            if (node.left != null) stringHelper(node.left, sb);
            sb.append(node.key).append("\n");
            if (node.right != null) stringHelper(node.right, sb);
        }
    }

    public K floor(K key) {
        checkEmpty();
        return floorHelper(root, key);
    }

    private K floorHelper(Node node, K key) {
        int comp = key.compareTo(node.key);
        if (node == null)       return null;
        else if (comp == 0)     return successor(node);
        else if (comp > 0)      return floorHelper(node.right);
        else                    return floorHelper(node.left);
    }

    private K successor(Node node) {
        K s = node.left == null ? node.key : max(node.left);
        return s;
    }

    private K max(Node node) {
        if (node.right == null)     return node.key;
        else                        return max(node.right);
    }

    private boolean isLeaf(Node node) { return node.left == null && node.right == null; }

}
