class Edge {
  private String code_src;
  private String code_dest;
  private int time;

  public Edge(String cs, String cd, int t) {
    code_src = cs;
    code_dest = cd;
    time = t;
  }

  public String getSrc() { return code_src; }
  public String getDest() { return code_dest; }
  public int getTime() { return time; }
}
