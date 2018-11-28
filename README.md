# CSCI 1102 Computer Science 2

### Fall 2018

##### Instructor R. Muller

Boston College

---

## Problem Set 9 : Final Problem Set

### 10 Points up to 16 Points

### Due Friday December 7, 2018 Midnight

This is an individual problem set. Choose any one of the following four options.

## Option 1: (10 Points up to 14 Points) Implement Immutable Ordered Maps using Immutable BSTs

Sedgewick and Wayne's [Binary Search Trees](http://algs4.cs.princeton.edu/32bst/BST.java.html) implement *mutable* maps from ordered keys to values. SW BSTs have a header node with a `root` field that may be `null` or may point to a value of type `Node`.  Insertions and deletions take place by mutating fields in the tree structure.

```java
...
  void put(Key key, Value val);
  void deleteMin();
  void deleteMax();
  void delete(Key key);        // Hibbard Deletion
...
```

By altering the map's interface somewhat, we can implement *immutable* or persistent maps in which alterations to the map construct a new map.  The following interface can be used as a specification for immutable maps with ordered keys.

```java
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
```

Immutable BSTs can be implemented along the lines that we used in implementing the simpler Immutable *unordered* map API in week 08. That API was

```java
public interface Map<Key, Value> {
  Value get(Key key);
  Map<Key, Value> put(Key key, Value value);
  int size();
  boolean isEmpty();
  String toString();
}
```

That implementation used two classes: an `EmptyMap` and a `LinkedMap`. Because the keys were unordered, operations such as `get` were implemented by scanning through a linked list. This required a linear number of steps. In the present situation, the keys are ordered so we can use a BST implementation which can provide better resource consumption when the tree is in balance. For Option 1, you are not required to ensure that the BST remains balanced.

### Notes

+ Like the SW implementation of mutable BSTs, your implementation should throw a `NoSuchElementException` if a client attempts to deletea key/value pair that doesn't exist in the BST. 
+ The `toString` function should return a simple string representation of the tree. Consider using `--` for empty trees and `(left, key, val, right)` for non-empty trees.
+ As usual, your classes should include `main` functions that implement unit tests for the class.

### Extra Credit: up to 4 points

1. (1 Point) Implement the `Key select(int k)` operation. See SW for a description.
2. (1 Point) Implement the `int rank(Key key)` operation. See SW for a description.
3. (1 Point) implement the `Map<Key, Value> deleteMin()` operation. See SW for a description. 
4. (1 Point) Implement the `Map<Key, value> delete(Key key)` operation. See SW for a description.
5. (2 Point) Implement the `Iterable<Key> keys()`operation. See SW for a description.

### Option 2: (16 Points) Implement Immutable Ordered Maps using Balanced Left-Leaning Red/Black Trees

This option is like Option 1 except that the BST is required to be balanced.

```java
public class RedBlack<Key extends Comparable<Key>, Value> implements Map<Key, Value> {
  YOUR CODE
}
```

SW's Red/Black Tree code implements *mutable* maps via mutating tree rotations. In order to implement an *immutable* map with balanced BSTs, consider constructing new nodes when rotations or color flips are required.  Include appropriate unit testing per usual.

### Option 3: (15 Points) Develop a Skip List Implementation of Mutable Maps with Ordered Keys

Refer to the description of Skip Lists in the accompanying article [skiplistsOriginal.pdf](./skiplistsOriginal.pdf). For this Option, your code should implement the following API

```java
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
```

```java
public class SkipList<Key extends Comparable<Key>, Value> implements            MutableOrdredMap<Key, Value> { 
  YOUR CODE
}
```

 Include appropriate unit testing per usual.

### Option 4: (10 Points) The Great Tree List Recursion Problem

This is a tough, interesting problem from the Web Exercises of the Sedgewick/Wayne website (Section 3.2). A fuller discussion of the problem can be found [here](http://cslibrary.stanford.edu/109/TreeListRecursion.html). Note that the "fuller discussion" includes a solution(!) so you'll have to be self-disciplined to avoid looking at it. A binary search tree and a circular doubly linked list are conceptually built from the same type of nodes - a data field and two references to other nodes. Given a binary search tree, rearrange the references so that it becomes a circular doubly-linked list (in sorted order). Nick Parlante describes this as one of the neatest recursive pointer problems ever devised. 

*Hint*: create a circularly linked list A from the left subtree, a circularly linked list B from the right subtree, and make the root a one node circularly linked list. Then merge the three lists.