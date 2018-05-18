class tNode<T> {
  private T item;
  private tNode<T> left;
  private tNode<T> right;

  public tNode(T item, tNode<T> left, tNode<T> right) {
    this.item = item;
    this.left = left;
    this.right = right;
  }
  public tNode(T item) {
    this(item, null, null);
  }
  public tNode() {
    this(null, null, null);
  }
  public T getItem() { return this.item; }
  public void setItem(T item) { this.item = item; }
  public tNode<T> getLeft() { return this.left; }
  public void setLeft(tNode<T> left) { this.left = left; }
  public tNode<T> getRight() { return this.right; }
  public void setRight(tNode<T> right) { this.right = right; }
}
