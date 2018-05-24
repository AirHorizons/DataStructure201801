class testBST {

  public static void main(String[] args) {
    try {
      BST<Integer, Integer> bst = new BST<Integer, Integer>();
      bst.insert(1, 1);
      bst.insert(1, 2);
      bst.insert(1, 3);
      bst.insert(2, 1);
      bst.insert(0, 1);
      bst.insert(2, 2);
      bst.delete(1);
      bst.preorder();
    }
    catch (ItemNotFoundException e) {
      e.printStackTrace();
    }
  }
}
