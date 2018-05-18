class BST<T extends Comparable<T>> {
  tNode<T> root;
  
  public boolean isEmpty() { return root==null; }
  
  // Insert
  //------------------------------------------------//
  public tNode<T> insert(T item) {
    return insertItem(root, item);
  }
  protected tNode<T> insertItem(tNode Node, T item) {
    if (Node == null) {
      Node = new Node(item);
    }
    else if (item.compareTo(Node.getItem()) < 0) {
      Node.setLeft(insertItem(Node.getLeft(), item));
    }
    else if (item.compareTo(Node.getItem()) > 0) {
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
      if (item.equals(Node.getItem())) return Node;
      else if (item.compareTo(Node.getItem()) < 0)
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
      if (item.equals(Node.getItem())) {
        Node = deleteNode(Node);
      }
      else if (item.compareTo(Node.getItem()) < 0) {
        Node.setLeft(Node.deleteItem(Node.getLeft(), item));
      }
      else
        Node.setRight(Node.deleteItem(Node.getRight(), item));
    }
  }
  protected tNode<T> deleteNode(tNode<T> Node) {
    if (Node.getLeft() == null && Node.getRight() == null) return null;
    else if (Node.getRight() == null) return tNode.getLeft();
    else if (Node.getLeft() == null) return tNode.getRight();
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

    System.out.print(item.toString());
    preorder_R(Node.getLeft());
    preorder_R(Node.getRight());
  }
}
