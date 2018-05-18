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
  protected tNode<T> retrieveItem(tNode Node, T item) {
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
  protected tNode<T> deleteItem(tNode Node, T item) throws ItemNotFoundException {
    if (Node == null) throw new ItemNotFoundException("Deletion Failed: No such Item");
    else {

    }
  }

}
