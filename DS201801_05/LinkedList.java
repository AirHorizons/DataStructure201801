class LinkedList<T> {

  private Node<T> head;
  private int numItems; 

  public LinkedList() {
    this.head = new Node<T>();
    this.numItems = 0;
  }
  public boolean isEmpty() { return numItems==0; }
  public int size() {
    return numItems;
  }
  public void add(T item) {
    Node<T> curr = head;

    while(curr.next != null)
      curr = curr.getNext();
    curr.setNext(new Node<T>(item, null));et
    numItems++;

    return;
  }
  public T remove(int n) throws IndexOutOfBoundaryException {
    Node<T> curr, prev;
    try {
      prev = find(n-1);
      curr = prev.getNext();
      if (curr == null) throw new IndexOutOfBoundaryException("Remove Failed: Index out of Boundary");

      prev.setNext(curr.getNext());
      numItems--;
      return curr.getItem();
    }
    catch (IndexOutOfBoundaryException e) {
      throw e;
    }
  }
  public T get(int n) throws IndexOutOfBoundaryException {
    if (n >= 0 && n < numItems) {
      return find(n).getItem();
    }
    else throw new IndexOutOfBoundaryException("Get Failed: Index out of Boundary");
  }
  private Node<T> find(int n) {
    Node<T> curr = head.getNext();

    for (int i=0; i<numItems; i++) {
      curr = curr.getNext();
    }
    return curr;
  }
  public void removeAll() { 
    head.next = null;
    numItems = 0;
  }
}
