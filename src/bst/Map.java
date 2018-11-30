// Immutable BST-Based Ordered Map API
// Ryan Krawczyk

public interface Map<K extends Comparable<K>, V> {

    V get(K key);
    Map<K, V> put(K key, V val);
    boolean isEmpty();
    int size();
    boolean contains(K key);
    K min();
    String toString();
    K floor(K key);

}
