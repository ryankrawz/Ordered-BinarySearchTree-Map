// Node ADT
// Ryan Krawczyk

public class NodeC<K extends Comparable<K>, V> implements Node<K, V> {

    private K key;
    private V val;
    private Node<K, V> left, right;

    NodeC(K key, V val, Node<K, V> left, Node<K, V> right) {
        this.key = key;
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public void setRight(Node node) { right = node; }

    public void setLeft(Node node) { left = node; }

    public Node<K, V> getRight() { return right; }

    public Node<K, V> getLeft() { return left; }

    public void setKey(K item) { key = item; }

    public void setVal(V item) { val = item; }

    public K getKey() { return key; }

    public V getVal() { return val; }

}
