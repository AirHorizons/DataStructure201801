class LinkedList<T> {

  private Node<T> head;
  private Node<T> tail;
  private int numItems; 

  public LinkedList() {
    this.head = new Node<T>();
    this.tail = null;
    this.numItems = 0;
  }
  public boolean isEmpty() { return numItems==0; }
  public int size() {
    return numItems;
  }

  /***********************************************
   *
   *      ADD
   *
   ***********************************************/
  public void add(T item) {
    if (tail != null) {
      tail.setNext(new Node<T>(item, null));
      tail = tail.getNext();
      numItems++;
      return;
    }
    else {
      head.setNext(new Node<T>(item, null));
      tail = head.getNext();
      numItems++;
      return;
    }
  }

  /***********************************************
   *
   *     REMOVE 
   *
   ***********************************************/
  public T remove(int n) throws IndexOutOfBoundaryException {
    Node<T> curr, prev;
    try {
      if (n >= 0 && n < numItems) {
        prev = find(n-1);
        curr = prev.getNext();

        prev.setNext(curr.getNext());
        numItems--;
        return curr.getItem();
      }
      else throw new IndexOutOfBoundaryException("Remove Failed: Index out of Boundary");
    }
    catch (IndexOutOfBoundaryException e) {
      throw e;
    }
  }

  /***********************************************
   *
   *     GET 
   *
   ***********************************************/
  public T get(int n) throws IndexOutOfBoundaryException {
    if (n >= 0 && n < numItems) {
      return find(n).getItem();
    }
    else throw new IndexOutOfBoundaryException("Get Failed: Index out of Boundary");
  }
  private Node<T> find(int n) {
    if (n == -1) return head;
    Node<T> curr = head.getNext();

    for (int i=0; i<n; i++) {
      curr = curr.getNext();
    }
    return curr;
  }

  /***********************************************
   *
   *     REMOVEALL 
   *
   ***********************************************/
  public void removeAll() { 
    head.setNext(null);
    tail = null;
    numItems = 0;
  }

  /***********************************************
   *
   *     PRINT 
   *
   ***********************************************/
  public void print() {
    System.out.println(this.toString());
  }

  @Override
  public String toString() {
    Node<T> curr = head.getNext();
    String result = "";
    while(curr != null) {
      String s = curr.getItem().toString();
      result += s;
      if (curr.getNext() != null)
        result += " ";
      curr = curr.getNext();
    }
    return result;
  }
}
