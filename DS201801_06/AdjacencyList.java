import java.util.*;
class AdjacencyList {
    private HashMap<String, Station> map;
    private HashMap<String, ArrayList<String>> namemap;

    public AdjacencyList() {
        map = new HashMap<String, Station>();
        namemap = new HashMap<String, ArrayList<String>>();
    }

    // add station, and if there is transfer station, add edge to all the same station in different lines.
    public void addStation(Station st){
        map.put(st.getCode(), st);
        ArrayList<String> codelist = namemap.get(st.getName());
        if (codelist != null) {
            for (String code : codelist) {
                Station st_old = map.get(code);
                map.put(code, st_old.addConnected(new Edge(st_old.getCode(), st.getCode(), 0, true)));
                map.put(st.getCode(), map.get(st.getCode()).addConnected(new Edge(st.getCode(), st_old.getCode(), 0, true)));
            }
            codelist.add(st.getCode());
            namemap.put(st.getName(), codelist);
        }
        else {
            codelist = new ArrayList<String>();
            codelist.add(st.getCode());
            namemap.put(st.getName(), codelist);
        }
    }

    public void addEdge(Edge e) {
        map.put(e.getSrc(), map.get(e.getSrc()).addConnected(e));
    }

    public String toString() {
      String s = "";
      for (Station st : map.values()) {
        s += st.getCode() + " " + st.getName() + " " + st.getLine() + "\n";
        for (Edge e : st.getConnected()){
          s += "\t" + e.getSrc() + " " + e.getDest() + " " + e.getTime() + "\n";
        }
      }
      return s;
    }
}
