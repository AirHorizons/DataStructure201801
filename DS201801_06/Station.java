import java.util.*;
class Station {
  private String code;
  private String name;
  private String line;
  private ArrayList<Edge> connected;
  public Station (String c, String n, String l) {
    code = c;
    name = n;
    line = l;
    connected = new ArrayList<Edge>();
  }

  /*
    MISC
   */

  // getters
  public String getCode() { return code; }
  public String getName() { return name; }
  public String getLine() { return line; }
  public ArrayList<Edge> getConnected() {
      return connected;
  }

  // setters
  public Station addConnected(Edge e) {
    connected.add(e);
    return this;
  }
}
