// Node API
// Ryan Krawczyk

public interface Node<K extends Comparable<K>, V> {

    void setRight(Node node);
    void setLeft(Node node);
    Node<K, V> getRight();
    Node<K, V> getLeft();
    void setKey(K item);
    void setVal(V item);
    K getKey();
    V getVal();

}
