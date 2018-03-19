public class Node{
  // data
  private Object item;
  private Node next;

  // constructor
  public Node(Object newItem){
    item = newItem;
    next = null;
  }
  public Node(Object newItem, Node nextNode){
    item = newItem;
    next = nextNode;
  }

  // getter, setter of item
  public void setItem(Object newItem){
    item = newItem;
  }
  public Object getItem(){
    return item;
  }

  // getter, setter of next
  public void setNext(Node nextNode){
    next = nextNode;
  }
  public Node getNext(){
    return next;
  }

}
