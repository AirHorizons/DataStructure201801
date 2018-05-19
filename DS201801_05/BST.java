import java.util.*;

class BST<Key extends Comparable<Key>, Value extends LinkedList<T>, T> {
  private tNode<Key, Value> root;
  private Comparator<Key> comparator;

  public BST() {
    root = null;
    comparator = null;
  }

  public BST(Comparator<Key> comparator) {
    root = null;
    this.comparator = comparator;
  }

  public int compare(Key x, Key y) {
    if (comparator == null)
      return x.compareTo(y);
    else
      return comparator.compare(x, y);
  }
  
  public boolean isEmpty() { return root==null; }

  // Insert
  //------------------------------------------------//
  public tNode<Key, Value> insert(Key key, T item) {
    return insertItem(root, key, item);
  }
  protected tNode<Key, Value> insertItem(tNode<Key, Value> Node, Key key, T item) {
    if (Node == null) {
      Value ll = (Value)new LinkedList<T>();
      ll.add(item);
      Node = new tNode<Key, Value>(key, ll);
    }
    else if (compare(key, Node.getKey()) == 0) {
      Node.getValue().add(item);
    }
    else if (compare(key, Node.getKey()) < 0) {
      Node.setLeft(insertItem(Node.getLeft(), key, item));
    }
    else if (compare(key, Node.getKey()) > 0) {
      Node.setRight(insertItem(Node.getRight(), key, item));
    }
    return Node;
  }
  // Retrieve
  //------------------------------------------------//
  public tNode<Key, Value> retrieve(Key key) {
    return retrieveItem(root, key);
  }
  protected tNode<Key, Value> retrieveItem(tNode<Key, Value> Node, Key key) {
    if (Node == null) return null;
    else {
      if (compare(key, Node.getKey()) == 0) return Node;
      else if (compare(key, Node.getKey()) < 0)
        return retrieveItem(Node.getLeft(), key);
      else 
        return retrieveItem(Node.getRight(), key);
    }
  }
  // Delete
  //------------------------------------------------//
  public tNode<Key, Value> delete(Key key) throws ItemNotFoundException {
    try {
      return deleteItem(root, key);
    }
    catch (ItemNotFoundException e) {
      throw e;
    }
  } 
  protected tNode<Key, Value> deleteItem(tNode<Key, Value> Node, Key key) throws ItemNotFoundException {
    if (Node == null) throw new ItemNotFoundException("Deletion Failed: No such Item");
    else {
      if (compare(key, Node.getKey()) == 0) {
        Node = deleteNode(Node);
      }
      else if (compare(key, Node.getKey()) < 0){
        Node.setLeft(deleteItem(Node.getLeft(), key));
      }
      else
        Node.setRight(deleteItem(Node.getRight(), key));
    }
    return Node;
  }
  protected tNode<Key, Value> deleteNode(tNode<Key, Value> Node) {
    if (Node.getLeft() == null && Node.getRight() == null) return null;
    else if (Node.getRight() == null) return Node.getLeft();
    else if (Node.getLeft() == null) return Node.getRight();
    else {
      // replace tNode with the biggest item of left subtree
      Node = replaceMax(Node.getLeft());
      Node.setLeft(deleteMax(Node.getLeft()));
      return Node;
    }
  }
  protected tNode<Key, Value> replaceMax(tNode<Key, Value> Node) {
    if (Node.getRight() == null) return Node;
    else return replaceMax(Node.getRight());
  }
  protected tNode<Key, Value> deleteMax(tNode<Key, Value> Node) {
    if (Node.getRight() == null) return Node.getLeft();
    else {
      Node.setRight(deleteMax(Node.getRight()));
      return Node;
    }
  }
  // Preorder Keyraverse
  //------------------------------------------------//
  public void preorder() {
    preorder_R(root);
  }
  private void preorder_R(tNode<Key, Value> Node) {
    if (Node == null) return;

    System.out.print(Node.getKey().toString() + ": " + Node.getValue().toString());
    preorder_R(Node.getLeft());
    preorder_R(Node.getRight());
  }
}
