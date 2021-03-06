class AVL<Key extends Comparable<Key>, Value> extends BST<Key, Value> {
 
  /*
   *
   *
   *  MISC
   *
   *
   */ 
  private int max(int a, int b){
    return (a>b)?a:b;
  }
  private int getHeight(tNode<Key, LinkedList<Value>> Node) {
    if (Node == null) return 0;
    return Node.getHeight();
  }
  private int getBalance(tNode<Key, LinkedList<Value>> Node) {
    if (Node == null) return 0;
    return getHeight(Node.getLeft())-getHeight(Node.getRight());
  }
  private tNode<Key, LinkedList<Value>> rotateLeft(tNode<Key, LinkedList<Value>> Node) {
    tNode<Key, LinkedList<Value>> rightNode = Node.getRight();
    Node.setRight(rightNode.getLeft());
    rightNode.setLeft(Node);
    Node.setHeight(max(getHeight(Node.getLeft()), getHeight(Node.getRight()))+1);
    rightNode.setHeight(max(getHeight(rightNode.getLeft()), getHeight(rightNode.getRight()))+1);
    return rightNode;
  } 
  private tNode<Key, LinkedList<Value>> rotateRight(tNode<Key, LinkedList<Value>> Node) {
    tNode<Key, LinkedList<Value>> leftNode = Node.getLeft();
    Node.setLeft(leftNode.getRight());
    leftNode.setRight(Node);
    Node.setHeight(max(getHeight(Node.getLeft()), getHeight(Node.getRight()))+1);
    leftNode.setHeight(max(getHeight(leftNode.getLeft()), getHeight(leftNode.getRight()))+1);
    return leftNode;
  } 

  /*
   *
   *
   *  INSERT
   *
   *
   */

  @Override
  public tNode<Key, LinkedList<Value>> insert(Key key, Value item) {
    return root = insertItem(root, key, item);
  }
  @Override
  public tNode<Key, LinkedList<Value>> insertItem(tNode<Key, LinkedList<Value>> Node, Key key, Value item) {
    Node = super.insertItem(Node, key, item);
    Node.setHeight(max(getHeight(Node.getLeft()), getHeight(Node.getRight()))+1);
    if (getBalance(Node) > 1 && compare(key, Node.getLeft().getKey()) < 0) {
      return rotateRight(Node);
    }
    if (getBalance(Node) < -1 && compare(key, Node.getRight().getKey()) > 0) {
      return rotateLeft(Node);
    }
    if (getBalance(Node) > 1 && compare(key, Node.getLeft().getKey()) > 0) {
      Node.setLeft(rotateLeft(Node.getLeft()));
      return rotateRight(Node);
    }
    if (getBalance(Node) < -1 && compare(key, Node.getRight().getKey()) < 0) {
      Node.setRight(rotateRight(Node.getRight()));
      return rotateLeft(Node);
    }

    return Node;
  }
  /*
   *
   *
   *  DELETE
   *
   *
   */
  @Override
  public tNode<Key, LinkedList<Value>> delete(Key key) throws ItemNotFoundException {
    try {
      return deleteItem(root, key);
    }
    catch (ItemNotFoundException e) {
      throw e;
    }
  }
  @Override
  public tNode<Key, LinkedList<Value>> deleteItem(tNode<Key, LinkedList<Value>> Node, Key key) throws ItemNotFoundException {
    try {
      Node = super.deleteItem(Node, key);
      Node.setHeight(max(getHeight(Node.getLeft()), getHeight(Node.getRight()))+1);
      if (getBalance(Node) > 1 && getBalance(Node.getLeft()) >= 0) {
        return rotateRight(Node);
      }
      if (getBalance(Node) > 1 && getBalance(Node.getLeft()) < 0) {
        Node.setLeft(rotateLeft(Node.getLeft()));
        return rotateRight(Node);
      } 
      if (getBalance(Node) < -1 && getBalance(Node.getRight()) <= 0) {
        return rotateLeft(Node);
      }
      if (getBalance(Node) < -1 && getBalance(Node.getRight()) > 0) {
        Node.setRight(rotateRight(Node.getRight()));
        return rotateLeft(Node);
      }
      return Node;
    }
    catch (ItemNotFoundException e) {
      throw e;
    }
  }
}
