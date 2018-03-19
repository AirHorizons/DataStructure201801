public class LListSingle implements LListInterface {
  private Node head;
  private int length;

  public LListSingle(){
    head = new Node(new Integer(0));
    length = 0;
  }

  public boolean isEmpty() { return length == 0; }

  public int size() { return length; }

  // private for Node search
  private Node find(int index) {
    Node cur = head.getNext();
    for (int i=0; i<index ; i++) {
      cur = cur.getNext();
    }
    return cur;
  }

  public void add(int index, Object item) throws IndexOutOfBoundaryException {
    if ((index >= 0) && (index <= length)){
      Node prev = find(index-1);
      Node newNode = new Node(item, prev.getNext());
      prev.setNext(newNode);
      length++;
    }
    else {
      throw new IndexOutOfBoundaryException("Index out of boundary.\n");
    }
  }

  public void remove(int index) throws IndexOutOfBoundaryException {
    if  ((index >= 0) && (index <= length)){
      Node prev = find(index-1);
      Node cur = prev.getNext();
      prev.setNext(cur.getNext());
      length--;
    }
    else {
      throw new IndexOutOfBoundaryException("Index out of boundary.\n");
    }
  }

  public Object get(int index) throws IndexOutOfBoundaryException {
    if ((index >= 0 && index <= length)) {
      Node cur = find(index);
      return cur.getItem();
    }
    else {
      throw new IndexOutOfBoundaryException("Index out of boundary.\n");
    }
  }

  public void removeAll() {
    for (int i=0; i<length; i++) {
      try {
        remove(0);
      }
      catch (IndexOutOfBoundaryException e){
        System.err.println(e.getMessage());
      }
    }
    length = 0;
  }
}
