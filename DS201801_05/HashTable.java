import java.util.*;

class HashTable<Key extends Comparable<Key>, Value> {
  private ArrayList<AVL<Key, Value>>  Buckets;
  private final int HASHSIZE = 100;

  public HashTable() {
    Buckets = new ArrayList<AVL<Key, Value>>(HASHSIZE);
    for (int i=0; i<HASHSIZE; i++)
      Buckets.get(i) = new AVL();
  }

  public void insert(Key key, Value value) {
    Buckets.get(getHash(key)).insert(key, value);
  }

  public LinkedList<Value> retrieve(Key key) {
    tNode<Key, LinkedList<Value>> Node = Buckets.get(getHash(key)).retrieve(key);
    if (Node != null) return Node.getValue();
    else return null;
  }

  public int getHash(Key key) {
    int length = key.toString().length();
    int result = 0;
    for (int i=0; i< ((length < 6) ? length : 6); i++) {
      result += (int)key.toString().charAt(i);
    }
    return result % HASHSIZE;
  }
}

