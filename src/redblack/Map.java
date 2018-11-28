public interface Map<Key extends Comparable<Key>, Value> {
  Value get(Key key);
  Map<Key, Value> put(Key key, Value val);
  boolean isEmpty();
  int size();
  boolean contains(Key key);
  Key min();
  String toString();
  Key floor(Key key);
}
