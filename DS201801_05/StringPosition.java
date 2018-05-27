class StringPosition implements Comparable<StringPosition> {
  private int row;
  private int col;

  public StringPosition(int r, int c) {
    row = r;
    col = c;
  }
  public int getRow() { return this.row; }
  public int getCol() { return this.col; }
  public int compareTo(StringPosition other) {
    if (this.row != other.getRow()) return this.row-other.getRow();
    return this.col - other.getCol();
  }
  public int compareWithOffset(StringPosition other, int offset) {
    if (this.row != other.getRow()) return this.row-other.getRow();
    else return this.col + offset - other.getCol();
  }
  public boolean equals(StringPosition other) {
    return (this.row == other.getRow()) && (this.col == other.getCol());
  }
  @Override
  public String toString() {
    String result = "";
    result += "(" + row + ", " + col + ")";
    return result;
  }
}
