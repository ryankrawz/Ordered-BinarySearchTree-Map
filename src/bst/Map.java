// Immutable BST-Based Ordered Map API
// Ryan Krawczyk

public interface Map<K extends Comparable<K>, V> {

    V get(K key);
    Map<K, V> put(K key, V val, Map<K, V> bst);
    K getKey();
    V getVal();
    Map<K, V> getLeft();
    Map<K, V> getRight();
    boolean equals(Map<K, V> other);
    boolean isEmpty();
    int size();
    boolean contains(K key);
    K min();
    String toString();
    K floor(K key);
    K select(int n);
    int rank(K key);
    Map<K, V> deleteMin();
    Map<K, V> delete(K key);

}
