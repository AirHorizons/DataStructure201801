class testAVL {

  public static void main(String[] args) {
    try {
      AVL<Integer, Integer> avl = new AVL<Integer, Integer>();
      for (int i=0; i<3; i++) {
        avl.insert(1, i);
        avl.insert(2, i);
        avl.insert(3, i);
      }
      avl.insert(4, 0);
      avl.insert(5, 0);
      avl.insert(6, 0);
      avl.insert(7, 0);
      avl.insert(8, 0);
      avl.insert(9, 0);
      avl.insert(10, 0);
      avl.preorder();
      avl.delete(10);
      avl.delete(4);
      avl.insert(0, 1);
      avl.preorder();
    }
    catch (ItemNotFoundException e) {
      e.printStackTrace();
    }
  }
}
