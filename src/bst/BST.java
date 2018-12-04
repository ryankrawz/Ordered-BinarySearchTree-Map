// Binary Search Tree ADT
// Ryan Krawczyk

import java.util.NoSuchElementException;
import java.util.List;
import java.util.ArrayList;

public class BST<K extends Comparable<K>, V> implements Map<K, V> {

    private Node<K, V> root;
    private int size;

    public BST(Node<K, V> root, int size) {
        this.root = root;
        this.size = size;
    }

    public V get(K key) {
        checkEmpty();
        return getHelper(root, key);
    }

    // Parses tree using compareTo until matching key is located, returns null if key does not exist
    private V getHelper(Node<K, V> node, K key) {
        if (node == null) return null;
        else {
            int comp = key.compareTo(node.getKey());
            if (comp == 0)          return node.getVal();
            else if (comp > 0)      return getHelper(node.getRight(), key);
            else                    return getHelper(node.getLeft(), key);
        }
    }

    public Map<K, V> put(K key, V val) {
        size++;
        if (root == null)       root = new NodeC<K, V>(key, val, null, null);
        else                    putHelper(root, key, val);
        return this;
    }

    // Parses tree using compareTo until available spot (indicated by null) is located
    private void putHelper(Node<K, V> node, K key, V val) {
        int comp = key.compareTo(node.getKey());
        if (comp == 0) node.setVal(val);
        else if (comp > 0) {
            if (node.getRight() == null)    node.setRight(new NodeC<K, V>(key, val, null, null));
            else                            putHelper(node.getRight(), key, val);
        } else {
            if (node.getLeft() == null)     node.setLeft(new NodeC<K, V>(key, val, null, null));
            else                            putHelper(node.getLeft(), key, val);
        }
    }

    public boolean isEmpty() { return size == 0; }

    // Throws exception for operations requiring the tree to contain items
    private void checkEmpty() { if (isEmpty()) throw new NoSuchElementException("EMPTY MAP"); }

    public int size() { return size; }

    public Node<K, V> root() { return root; }

    public boolean contains(K key) {
        checkEmpty();
        return containHelper(root, key);
    }

    // Parses tree using compareTo, returns false if entire branch has been visited
    private boolean containHelper(Node<K, V> node, K key) {
        if (node == null) return false;
        else {
            int comp = key.compareTo(node.getKey());
            if (comp == 0)          return true;
            else if (comp > 0)      return containHelper(node.getRight(), key);
            else                    return containHelper(node.getLeft(), key);
        }
    }

    public K min() {
        checkEmpty();
        return minHelper(root);
    }

    // Recursively walks left to locate min (employs relational invariant)
    private K minHelper(Node<K, V> node) {
        if (node.getLeft() == null)     return node.getKey();
        else                            return minHelper(node.getLeft());
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
    private void stringHelper(Node<K, V> node, StringBuilder sb) {
        if (isLeaf(node)) sb.append(node.getKey()).append(" --> ").append(node.getVal()).append("\n");
        else {
            if (node.getLeft() != null) stringHelper(node.getLeft(), sb);
            sb.append(node.getKey()).append(" --> ").append(node.getVal()).append("\n");
            if (node.getRight() != null) stringHelper(node.getRight(), sb);
        }
    }

    public K floor(K key) {
        checkEmpty();
        return floorHelper(root, key);
    }

    // Parses tree using compareTo, calls successor on input key
    private K floorHelper(Node<K, V> node, K key) {
        if (node == null) return null;
        else {
            int comp = key.compareTo(node.getKey());
            if (comp == 0)          return successor(node).getKey();
            else if (comp > 0)      return floorHelper(node.getRight(), key);
            else                    return floorHelper(node.getLeft(), key);
        }
    }

    // Returns the maximum key of the left field of the input node
    private Node<K, V> successor(Node<K, V> node) {
        Node<K, V> s = node.getLeft() == null ? null : max(node.getLeft());
        return s;
    }

    // Recursively walks right to locate max (employs relational invariant)
    private Node<K, V> max(Node<K, V> node) {
        if (node.getRight() == null)        return node;
        else                                return max(node.getRight());
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
    private void indexHelper(Node<K, V> node, List<K> l) {
        if (isLeaf(node)) l.add(node.getKey());
        else {
            if (node.getLeft() != null) indexHelper(node.getLeft(), l);
            l.add(node.getKey());
            if (node.getRight() != null) indexHelper(node.getRight(), l);
        }
    }

    public Map<K, V> deleteMin() {
        checkEmpty();
        if (root.getLeft() == null)         root = null;
        else                                deleteMinHelper(root.getLeft(), root);
        return this;
    }

    // Locates minimum with recursive walk left, resets trailing pointer to null
    private void deleteMinHelper(Node<K, V> node1, Node<K, V> node2) {
        if (node1.getLeft() == null)        node2.setLeft(node1.getRight());
        else                                deleteMinHelper(node1.getLeft(), node2.getLeft());
    }

    public Map<K, V> delete(K key) {
        checkEmpty();
        int comp = key.compareTo(root.getKey());
        boolean check = (comp > 0 && root.getRight() == null) || (comp < 0 && root.getLeft() == null);
        if (!check) {
            if (comp > 0)           deleteHelper(root.getRight(), root, key);
            else if (comp < 0)      deleteHelper(root.getLeft(), root, key);
            else {
                Node<K, V> s = successor(root);
                if (s != null) {
                    s.setRight(root.getRight());
                    if (root.getLeft().getKey().compareTo(s.getKey()) == 0)     s.setLeft(root.getLeft().getLeft());
                    else                                                        s.setLeft(root.getLeft());
                    eliminateSuccessor(root.getLeft(), root, 0);
                }
                root = s;
            }
        }
        return this;
    }

    // Locates input key using compareTo, reconnects trailing pointer to nodes following input key
    private void deleteHelper(Node<K, V> node1, Node<K, V> node2, K key) {
        int comp1 = key.compareTo(node1.getKey());
        int comp2 = key.compareTo(node2.getKey());
        if (comp1 == 0) {
            Node<K, V> s = successor(node1);
            if (s != null) {
                s.setRight(node1.getRight());
                s.setLeft(node1.getLeft());
            }
            if (comp2 > 0) {
                if (s == null) node2.setRight(node1.getRight());
                else {
                    eliminateSuccessor(node2.getRight().getLeft(), node2.getRight(), 0);
                    node2.setRight(s);
                }
            } else {
                if (s == null) node2.setLeft(node1.getRight());
                else {
                    eliminateSuccessor(node2.getLeft().getLeft(), node2.getLeft(), 0);
                    node2.setLeft(s);
                }
            }
            return;
        }
        else if (comp1 > 0 && node1.getRight() != null)     deleteHelper(node1.getRight(), node1, key);
        else if (comp1 < 0 && node1.getLeft() != null)      deleteHelper(node1.getLeft(), node1, key);
        else                                                return;
    }

    // Sets pointers to successor of node in deleteHelper equal to null once successor is in place of deleted key
    private void eliminateSuccessor(Node<K, V> node1, Node<K, V> node2, int c) {
        if (node1.getRight() == null && c == 0) node2.setLeft(node1.getLeft());
        else {
            if (node1.getRight() == null)   node2.setRight(node1.getRight());
            else                            eliminateSuccessor(node1.getRight(), node1, c + 1);
        }
    }

    private boolean isLeaf(Node<K, V> node) { return node.getLeft() == null && node.getRight() == null; }

}
