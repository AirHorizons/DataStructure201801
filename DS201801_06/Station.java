import java.util.*;
class Station {
  private String code;
  private String name;
  private String line;
  private int distance;
  private String previous; // Name of the previous station on the shortest path
  private ArrayList<Edge> connected;
  public Station (String c, String n, String l) {
    code = c;
    name = n;
    line = l;
    distance = Integer.MAX_VALUE;
    previous = null;
    connected = new ArrayList<Edge>();
  }

  /*
    MISC
   */

  // getters
  public String getCode() { return code; }
  public String getName() { return name; }
  public String getLine() { return line; }
  public int getDistance() { return distance; }
  public String getPrevious() { return previous; }
  public ArrayList<Edge> getConnected() {
      return connected;
  }
  public ArrayList<String> getDest() {
    ArrayList<String> al = new ArrayList<String>();
    for (Edge e : connected) {
      al.add(e.getDest());
    }
    return al;
  }

  // setters
  public Station addConnected(Edge e) {
    connected.add(e);
    return this;
  }
  public void setDistance(int d) {
    distance = d;
  }
  public void setPrevious(String p) {
    previous = p;
  }
}
