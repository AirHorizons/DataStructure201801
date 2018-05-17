class LinkedList<T> {

  private Node<T> head;
  private int numItems; 

  public LinkedList() {
    this.head = new Node<T>();
    this.numItems = 0;
  }
  public int size() {
    return numItems;
  }
  public void add(T item) {
    Node<T> curr = head;

    while(curr.next != null)
      curr = curr.getNext();
    curr.setNext(new Node<T>(item, null));
    numItems++;

    return;
  }
  public T remove(int n) throws IndexOutOfBoundaryException {
    Node<T> curr, prev;
    try {
      prev = find(n-1);
      curr = prev.getNext();

      if (curr == null) throw new IndexOutOfBoundaryException();

      prev.setNext(curr.getNext());
      numItems--;
      return curr;
    }
    catch (IndexOutOfBoundaryException e) {
      throw e;
    }
  }
  private T find(int n) throws IndexOutOfBoundaryException {
    int i=0;
    Node<T> curr = head;

    if (n > numItems) throw new IndexOutOfBoundaryException();
    if (n == -1) return head;

    do {
      curr = curr.getNext();
      i++
    } while(i < n);

    return curr;
  }
}
