class tNode<Key, Value> {
  private Key key;
  private Value value;
  private tNode<Key, Value> left;
  private tNode<Key, Value> right;
  private int height;

  public tNode(Key key, Value value, tNode<Key, Value> left, tNode<Key, Value> right) {
    this.key = key;
    this.value = value;
    this.left = left;
    this.right = right;
    this.height = 0;
  }
  public tNode(Key key, Value value) {
    this(key, value, null, null);
  }
  public tNode() {
    this(null, null, null, null);
  }
  public Key getKey() { return this.key; }
  public void setKey(Key key) { this.key = key; }
  public Value getValue() { return this.value; }
  public void setValue(Value value) { this.value = value; }
  public tNode<Key, Value> getLeft() { return this.left; }
  public void setLeft(tNode<Key, Value> left) { this.left = left; }
  public tNode<Key, Value> getRight() { return this.right; }
  public void setRight(tNode<Key, Value> right) { this.right = right; }
  public int getHeight() { return this.height; }
  public void setHeight(int height) { this.height = height; }
}
