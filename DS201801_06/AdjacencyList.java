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
                Edge e1 = new Edge(st_old.getCode(), st.getCode(), 5, true);
                Edge e2 = new Edge(st.getCode(), st_old.getCode(), 5, true);
                map.put(code, st_old.addConnected(e1));
                map.put(st.getCode(), st.addConnected(e2));
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

    public int getDistance(String code_src, String code_dst) {
      Station src = map.get(code_src);
      for (Edge e:src.getConnected()) {
        if (e.getDest().equals(code_dst)) { 
          return e.getTime();
        }
      }
      return Integer.MAX_VALUE;
    }

    public void setDistance(String code, int dist) {
      Station st = map.get(code);
      st.setDistance(dist);
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

    public Station getStationByName(String name) {
      String code = namemap.get(name).get(0);
      if (code == null) return null;
      Station st = map.get(code);
      return st;
    }
    public Station getStationByCode(String code) {
      return map.get(code);
    }
    public ArrayList<String> getTransferCode(String name) {
      return namemap.get(name);
    }
    public HashMap<String, Station> getMap(){
      return map;
    }
}
