import java.util.*;

class BST<T extends Comparable<T>> {
  private tNode<T> root;
  private Comparator<T> comparator;

  public BST() {
    root = null;
    comparator = null;
  }

  public BST(Comparator<T> comparator) {
    root = null;
    this.comparator = comparator;
  }

  public int compare(T x, T y) {
    if (comparator == null)
      return x.compareTo(y);
    else
      return comparator.compare(x, y);
  }
  
  public boolean isEmpty() { return root==null; }

  // Insert
  //------------------------------------------------//
  public tNode<T> insert(T item) {
    return insertItem(root, item);
  }
  protected tNode<T> insertItem(tNode<T> Node, T item) {
    if (Node == null) {
      Node = new tNode<T>(item);
    }
    else if (compare(item, Node.getItem()) < 0) {
      Node.setLeft(insertItem(Node.getLeft(), item));
    }
    else if (compare(item, Node.getItem()) > 0) {
      Node.setRight(insertItem(Node.getRight(), item));
    }
    return Node;
  }
  // Retrieve
  //------------------------------------------------//
  public tNode<T> retrieve(T item) {
    return retrieveItem(root, item);
  }
  protected tNode<T> retrieveItem(tNode<T> Node, T item) {
    if (Node == null) return null;
    else {
      if (compare(item, Node.getItem()) == 0) return Node;
      else if (compare(item, Node.getItem()) < 0)
        return retrieveItem(Node.getLeft(), item);
      else 
        return retrieveItem(Node.getRight(), item);
    }
  }
  // Delete
  //------------------------------------------------//
  public tNode<T> delete(T item) throws ItemNotFoundException {
    try {
      return deleteItem(root, item);
    }
    catch (ItemNotFoundException e) {
      throw e;
    }
  } 
  protected tNode<T> deleteItem(tNode<T> Node, T item) throws ItemNotFoundException {
    if (Node == null) throw new ItemNotFoundException("Deletion Failed: No such Item");
    else {
      if (compare(item, Node.getItem()) == 0) {
        Node = deleteNode(Node);
      }
      else if (compare(item, Node.getItem()) < 0){
        Node.setLeft(deleteItem(Node.getLeft(), item));
      }
      else
        Node.setRight(deleteItem(Node.getRight(), item));
    }
    return Node;
  }
  protected tNode<T> deleteNode(tNode<T> Node) {
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
  protected tNode<T> replaceMax(tNode<T> Node) {
    if (Node.getRight() == null) return Node;
    else return replaceMax(Node.getRight());
  }
  protected tNode<T> deleteMax(tNode<T> Node) {
    if (Node.getRight() == null) return Node.getLeft();
    else {
      Node.setRight(deleteMax(Node.getRight()));
      return Node;
    }
  }
  // Preorder Traverse
  //------------------------------------------------//
  public void preorder() {
    preorder_R(root);
  }
  private void preorder_R(tNode<T> Node) {
    if (Node == null) return;

    System.out.print(Node.getItem().toString());
    preorder_R(Node.getLeft());
    preorder_R(Node.getRight());
  }
}
