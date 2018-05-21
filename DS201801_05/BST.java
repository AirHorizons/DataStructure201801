import java.util.*;

class BST<Key extends Comparable<Key>, Value> {
  protected tNode<Key, LinkedList<Value>> root;
  protected Comparator<Key> comparator;

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
  public tNode<Key, LinkedList<Value>> insert(Key key, Value item) {
    return root = insertItem(root, key, item);
  }
  protected tNode<Key, LinkedList<Value>> insertItem(tNode<Key, LinkedList<Value>> Node, Key key, Value item) {
    if (Node == null) {
      LinkedList<Value> ll = new LinkedList<Value>();
      ll.add(item);
      Node = new tNode<Key, LinkedList<Value>>(key, ll);
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
  public tNode<Key, LinkedList<Value>> retrieve(Key key) {
    return retrieveItem(root, key);
  }
  protected tNode<Key, LinkedList<Value>> retrieveItem(tNode<Key, LinkedList<Value>> Node, Key key) {
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
  public tNode<Key, LinkedList<Value>> delete(Key key) throws ItemNotFoundException {
    try {
      return root = deleteItem(root, key);
    }
    catch (ItemNotFoundException e) {
      throw e;
    }
  } 
  protected tNode<Key, LinkedList<Value>> deleteItem(tNode<Key, LinkedList<Value>> Node, Key key) throws ItemNotFoundException {
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
  protected tNode<Key, LinkedList<Value>> deleteNode(tNode<Key, LinkedList<Value>> Node) {
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
  protected tNode<Key, LinkedList<Value>> replaceMax(tNode<Key, LinkedList<Value>> Node) {
    if (Node.getRight() == null) return Node;
    else return replaceMax(Node.getRight());
  }
  protected tNode<Key, LinkedList<Value>> deleteMax(tNode<Key, LinkedList<Value>> Node) {
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
  private void preorder_R(tNode<Key, LinkedList<Value>> Node) {
    if (Node == null) return;

    System.out.println(Node.getKey().toString() + ": " + Node.getValue().toString());
    preorder_R(Node.getLeft());
    preorder_R(Node.getRight());
  }
}
