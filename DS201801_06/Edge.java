class Edge {
  private Station start;
  private Station end;
  private int time;

  public Edge(Station s, Station e, int t) {
    start = s;
    end = e;
    time = t;
  }

  public Station getStart() { return start; }
  public Station getEnd() { return end; }
  public int getTime() { return time; }
}
