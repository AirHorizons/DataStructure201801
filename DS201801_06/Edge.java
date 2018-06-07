class Edge {
  private String code_src;
  private String code_dest;
  private int time;
  private boolean is_transfer;

  public Edge(String cs, String cd, int t, boolean i_t) {
    code_src = cs;
    code_dest = cd;
    time = t;
    is_transfer = i_t;
  }

  public String getSrc() { return code_src; }
  public String getDest() { return code_dest; }
  public int getTime() { return time; }
  public boolean getTransfer() { return is_transfer; }
}
