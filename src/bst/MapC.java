// Immutable BST-Based Ordered Map ADT
// Ryan Krawczyk

import java.util.NoSuchElementException;
import java.util.List;
import java.util.ArrayList;

public class MapC<K extends Comparable<K>, V> implements Map<K, V> {

    private final Map<K, V> NONE = new EmptyMap<K, V>();

    private K key;
    private V val;
    private Map<K, V> left, right;
    private int N;

    public MapC(K key, V val, Map<K, V> left, Map<K, V> right) {
        this.key = key;
        this.val = val;
        this.left = left;
        this.right = right;
        this.N = left.size() + right.size() + 1;
    }

    public V get(K key) {
        checkEmpty();
        return getHelper(this, key);
    }

    // Parses with compareTo until matching key is located, returns null if key does not exist
    private V getHelper(Map<K, V> bst, K key) {
        if (bst.equals(NONE)) return null;
        else {
            int comp = key.compareTo(bst.getKey());
            if (comp == 0)          return bst.getVal();
            else if (comp > 0)      return getHelper(bst.getRight(), key);
            else                    return getHelper(bst.getLeft(), key);
        }
    }

    public Map<K, V> put(K key, V val) { return putHelper(this, key, val); }

    // Parses with compareTo until available spot (indicated by NONE) is located, recursively links new tree to old tree
    private Map<K, V> putHelper(Map<K, V> bst, K key, V val) {
        if (bst.equals(NONE)) return new MapC<K, V>(key, val, NONE, NONE);
        else {
            int comp = key.compareTo(bst.getKey());
            if (comp > 0) {
                Map<K, V> newRight = putHelper(bst.getRight(), key, val);
                return new MapC<K, V>(bst.getKey(), bst.getVal(), bst.getLeft(), newRight);
            } else if (comp < 0) {
                Map<K, V> newLeft = putHelper(bst.getLeft(), key, val);
                return new MapC<K, V>(bst.getKey(), bst.getVal(), newLeft, bst.getRight());
            } else {
                return new MapC<K, V>(bst.getKey(), val, bst.getLeft(), bst.getRight());
            }
        }
    }

    public K getKey() { return key; }

    public V getVal() { return val; }

    public Map<K, V> getLeft() { return left; }

    public Map<K, V> getRight() { return right; }

    public boolean equals(Map<K, V> other) { return size() == other.size(); }

    public boolean isEmpty() { return size() == 0; }

    // Throws exception for operations requiring the tree to contain items
    private void checkEmpty() { if (isEmpty()) throw new NoSuchElementException("EMPTY MAP"); }

    public int size() { return N; }

    public boolean contains(K key) {
        checkEmpty();
        return containHelper(this, key);
    }

    // Parses with compareTo, returns false if entire branch has been visited
    private boolean containHelper(Map<K, V> bst, K key) {
        if (bst.equals(NONE)) return false;
        else {
            int comp = key.compareTo(bst.getKey());
            if (comp == 0)          return true;
            else if (comp > 0)      return containHelper(bst.getRight(), key);
            else                    return containHelper(bst.getLeft(), key);
        }
    }

    public K min() {
        checkEmpty();
        return minHelper(this);
    }

    // Recursively walks left to locate minimum (employs relational invariant)
    private K minHelper(Map<K, V> bst) {
        if (bst.getLeft().equals(NONE))     return bst.getKey();
        else                                return minHelper(bst.getLeft());
    }

    public String toString() {
        checkEmpty();
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n***\n");
        stringHelper(this, sb);
        sb.append("***\n\n");
        return sb.toString();
    }

    // In-order traversal of tree, appends key and value
    private void stringHelper(Map<K, V> bst, StringBuilder sb) {
        if (isLeaf(bst)) sb.append(bst.getKey()).append(" --> ").append(bst.getVal()).append("\n");
        else {
            if (!bst.getLeft().equals(NONE)) stringHelper(bst.getLeft(), sb);
            sb.append(bst.getKey()).append(" --> ").append(bst.getVal()).append("\n");
            if (!bst.getRight().equals(NONE)) stringHelper(bst.getRight(), sb);
        }
    }

    public K floor(K key) {
        checkEmpty();
        return floorHelper(this, key);
    }

    // Parses with compareTo, calls khaleesi on input key to return successor
    private K floorHelper(Map<K, V> bst, K key) {
        if (bst.equals(NONE)) return null;
        else {
            int comp = key.compareTo(bst.getKey());
            if (comp == 0) {
                if (khaleesi(bst) != null)      return khaleesi(bst).getKey();
                else                            return null;
            }
            else if (comp > 0)      return floorHelper(bst.getRight(), key);
            else                    return floorHelper(bst.getLeft(), key);
        }
    }

    // Returns the maximum key of the left field of the input node
    private Map<K, V> khaleesi(Map<K, V> bst) {
        Map<K, V> s = bst.getLeft().equals(NONE) ? null : max(bst.getLeft());
        return s;
    }

    // Recursively walks right to locate max (employs relational invariant)
    private Map<K, V> max(Map<K, V> bst) {
        if (bst.getRight().equals(NONE))    return bst;
        else                                return max(bst.getRight());
    }

    // Retrieves index n - 1 from list of keys
    public K select(int n) {
        checkEmpty();
        List<K> l = new ArrayList<K>();
        indexHelper(this, l);
        return l.get(n - 1);
    }

    // Outputs rank based on key's location in list of keys
    public int rank(K key) {
        checkEmpty();
        List<K> l = new ArrayList<K>();
        indexHelper(this, l);
        return l.indexOf(key) + 1;
    }

    // Generates list of keys using in-order traversal
    private void indexHelper(Map<K, V> bst, List<K> l) {
        if (isLeaf(bst)) l.add(bst.getKey());
        else {
            if (!bst.getLeft().equals(NONE)) indexHelper(bst.getLeft(), l);
            l.add(bst.getKey());
            if (!bst.getRight().equals(NONE)) indexHelper(bst.getRight(), l);
        }
    }

    public Map<K, V> deleteMin() {
        checkEmpty();
        return deleteMinHelper(this);
    }

    // Locates minimum with recursive walk left, sets minimum node to NONE
    private Map<K, V> deleteMinHelper(Map<K, V> bst) {
        if (bst.getLeft().equals(NONE)) return NONE;
        else {
            Map<K, V> newLeft = deleteMinHelper(bst.getLeft());
            return new MapC<K, V>(bst.getKey(), bst.getVal(), newLeft, bst.getRight());
        }
    }

    public Map<K, V> delete(K key) {
        checkEmpty();
        return deleteHelper(this, key);
    }

    // Locates key with compareTo, sets value to null in accordance with get(K key) returning null for a nonexistent key
    private Map<K, V> deleteHelper(Map<K, V> bst, K key) {
        if (bst.equals(NONE)) return NONE;
        else {
            int comp = key.compareTo(bst.getKey());
            if (comp > 0) {
                Map<K, V> newRight = deleteHelper(bst.getRight(), key);
                return new MapC<K, V>(bst.getKey(), bst.getVal(), bst.getLeft(), newRight);
            } else if (comp < 0) {
                Map<K, V> newLeft = deleteHelper(bst.getLeft(), key);
                return new MapC<K, V>(bst.getKey(), bst.getVal(), newLeft, bst.getRight());
            } else {
                return new MapC<K, V>(bst.getKey(), null, bst.getLeft(), bst.getRight());
            }
        }
    }

    private boolean isLeaf(Map<K, V> bst) { return bst.getRight().equals(NONE) && bst.getLeft().equals(NONE); }

    public static void main(String[] args) {
        /* Unit Testing */
        Map<Integer, String> m1 = new EmptyMap<Integer, String>();

        // put(K key, V val)
        Map<Integer, String> m2 = m1.put(4, "D");
        Map<Integer, String> m3 = m2.put(3, "C");
        Map<Integer, String> m4 = m3.put(5, "E");
        Map<Integer, String> m5 = m4.put(2, "B");
        Map<Integer, String> m6 = m5.put(6, "F");
        Map<Integer, String> m7 = m6.put(1, "A");
        Map<Integer, String> m8 = m7.put(7, "G");
        System.out.format("%nTest map:%s", m8.toString());

        // get(K key)
        System.out.format("Value at key 4 is: %s", m8.get(4));
        System.out.format("%nValue at key 2 is: %s", m8.get(2));
        System.out.format("%nValue at key 7 is: %s%n%n", m8.get(7));

        // contains(K key)
        System.out.format("Map contains 1: %b", m8.contains(1));
        System.out.format("%nMap contains 8: %b", m8.contains(8));
        System.out.format("%nMap contains 5: %b", m8.contains(5));
        System.out.format("%nMap contains 11: %b%n%n", m8.contains(11));

        // min()
        System.out.format("Minimum of map is: %d%n%n", m8.min());

        // floor(K key)
        System.out.format("Floor of 2 is: %d", m8.floor(2));
        System.out.format("%nFloor of 3 is: %d%n%n", m8.floor(3));

        // select(int n)
        System.out.format("Key at rank 6 is: %d", m8.select(6));
        System.out.format("%nKey at rank 4 is: %d%n%n", m8.select(4));

        // rank(K key)
        System.out.format("Rank of key 4 is: %d", m8.rank(4));
        System.out.format("%nRank of key 7 is: %d%n%n", m8.rank(7));

        // deleteMin()
        System.out.format("Test map after deleting minimum:%s", m8.deleteMin().toString());

        // delete(K key)
        System.out.format("Test map after deleting key 6:%s", m8.delete(6).toString());
        System.out.format("Test map after deleting key 2:%s", m8.delete(2).toString());
    }

}
