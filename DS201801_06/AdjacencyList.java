import java.util.*;
class AdjacencyList {
    private HashMap<String, Station> map;

    public AdjacencyList() {
        map = new HashMap<String, Station>();
    }

    // add station, and if there is transfer station, add edge to all the same station in different lines.
    public void addStation(Station st){
        map.put(st.getCode(), st);
        for (Station st_map:map.values()) {
            // find station with same name
            if (st_map.getName() == st.getName() && st_map.getCode() != st.getCode()) {
                map.put(st_map.getCode(),st_map.addConnected(new Edge(st_map.getCode(), st.getCode(), 0)));
                map.put(st.getCode(), st.addConnected(new Edge(st.getCode(), st_map.getCode(), 0)));
            }
        }
    }

    public void addEdge(Edge e) {
        map.put(e.getSrc(), map.get(e.getSrc()).addConnected(e));
    }
}
