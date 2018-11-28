public interface MutableOrderedMap<Key extends Comparable<Key>, Value> {
  Value get(Key key);
  void put(Key key, Value val);
  void delete(Key key);
  boolean isEmpty();
  int size();
  boolean contains(Key key);
  Key min();
  String toString();
  Key floor(Key key);
}
