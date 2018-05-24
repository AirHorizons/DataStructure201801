class testLinkedList {
  public static void main(String[] args) {
    try {
      LinkedList<Integer> ll = new LinkedList<Integer>();
      for (int i=0; i < 10; i++) {
        ll.add(i);
      }
      ll.print();
      for (int i=4; i>=0; i--) {
        ll.remove(i);
      }
      ll.print();
    }
    catch (IndexOutOfBoundaryException e) {
      e.printStackTrace();
    }
  }
}
