// Empty Map ADT
// Ryan Krawczyk

public class EmptyMap<K extends Comparable<K>, V> implements Map<K, V> {

    public EmptyMap() {}

    public V get(K key) { return null; }

    public Map<K, V> put(K key, V val) { return new MapC<K, V>(key, val, this, this); }

    public K getKey() { return null; }

    public V getVal() { return null; }

    public Map<K, V> getLeft() { return null; }

    public Map<K, V> getRight() { return null; }

    public boolean equals(Map<K, V> other) { return size() == other.size(); }

    public boolean isEmpty() { return true; }

    public int size() { return 0; }

    public boolean contains(K key) { return false; }

    public K min() { return null; }

    public String toString() { return ""; }

    public K floor(K key) { return null; }

    public K select(int n) { return null; }

    public int rank(K key) { return 0; }

    public Map<K, V> deleteMin() { return null; }

    public Map<K, V> delete(K key) { return null; }

}
