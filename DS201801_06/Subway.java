import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Stack;
import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Subway {
    private static AdjacencyList al;
    private final static String input_pattern = "(.*) (.*) (.*)";
    private final static Pattern ip = Pattern.compile(input_pattern);
    private final static String query_pattern = "(.*) (.*)(\\s!)?";
    private final static Pattern qp = Pattern.compile(query_pattern);
    private final static int INPUT_STATION = 0;
    private final static int INPUT_EDGE = 1;
    private final static int INPUT_QUERY = 2;
    private final static int INF = Integer.MAX_VALUE;
    private static int state = 0;


    private static HashSet<Station> unvisited;
    private static HashSet<Station> visited;
    private static HashMap<Station, Integer> distances;
    private static HashMap<Station, Station> previous;
    
    public static void main(String[] args) {
      al = new AdjacencyList(); 
      try {
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        String input = "";

        while(true) {
          input = br.readLine();
          if (input == null) break;
          command(input);
        }
        state = INPUT_QUERY;
        br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
          input = br.readLine();
          if (input.equals("QUIT")) break;
          command(input);
        }
      }
      catch (IOException e) {
          System.out.println("잘못된 입력입니다. " + e.toString());
      }
    }

    public static void command(String input) {
      if (input.trim().isEmpty()) {
        state += 1;
        return;
      }
      Matcher m;
      String name, code, line, src, dest, time;
      switch(state) {
        case INPUT_STATION:
          m = ip.matcher(input);
          while (m.find()) {
            code = m.group(1);
            name = m.group(2);
            line = m.group(3);
            al.addStation(new Station(code, name, line));
          }
          break;
        case INPUT_EDGE:
          m = ip.matcher(input);
          while (m.find()) {
            src = m.group(1);
            dest = m.group(2);
            time = m.group(3);
            al.addEdge(new Edge(src, dest, Integer.parseInt(time), false));
          }
          break;
        case INPUT_QUERY:
          //System.out.println(al);
          m = qp.matcher(input);
          while (m.find()){
            if (m.group().length() == 4) findLeastTransferPath(m.group(1), m.group(2));
            else {
              findShortestPath(m.group(1), m.group(2));
            }
          }
          break;
        default: return;
      }
    }
    /*
        MISC
     */

    // print shortest path. If there is same name within the path, it is transfer station so merge it and wrap it with bracket.
    public static void findShortestPath(String start, String end) {
      unvisited = new HashSet<Station>();
      for (Station st: al.getMap().values()) {
        st.setDistance(INF);
        st.setPrevious(null);  
        unvisited.add(st);
      }
      Station newstation = null;
      for (String code : al.getTransferCode(start)){
        newstation = al.getStationByCode(code);
        newstation.setDistance(0);
      }
      while (!unvisited.isEmpty()){// && !newstation.getName().equals(end)) {
        //System.out.println(unvisited.size());
        newstation = getMin(unvisited);  
        //System.out.println(newstation.getName()+" "+newstation.getCode());
        unvisited.remove(newstation);      
        for (String s : newstation.getDest()) {
          Station updatestation = al.getStationByCode(s);
          if (!unvisited.contains(updatestation)) continue;
          int newdistance = addDistance(newstation.getDistance(), al.getDistance(newstation.getCode(), updatestation.getCode()));
            //if (updatestation.getName().equals("양재")) {
              //System.out.println(updatestation.getCode());
              //System.out.println("Original Distance: " + updatestation.getDistance() + " from " + ((updatestation.getPrevious() != null) ? al.getStationByCode(updatestation.getPrevious()).getName(): ""));
              //System.out.println("New Distance: " + newdistance + " from " + newstation.getName());
            //}            
          if (compareDistance(updatestation.getDistance(), newdistance) > 0) {
            updatestation.setDistance(newdistance);
            updatestation.setPrevious(newstation.getCode());
            //unvisited.add(updatestation);
            //System.out.println("\t" + updatestation.getName() + " " + updatestation.getCode() + " "+ al.getStationByCode(updatestation.getPrevious()).getName() +" "+updatestation.getPrevious());
          } 
        }
      }
      Station st = al.getStationByName(end);
      Stack<String> stack = new Stack<String>();
      Stack<String> beststack = new Stack<String>();
      String bestresult = "";
      int mintime = Integer.MAX_VALUE;
      int time=0;
      boolean first=true;
      String result="";
      for (String code : al.getTransferCode(end)){
        st = al.getStationByCode(code);
        stack = new Stack<String>();
        time = 0;
        while(true) {
          stack.push(st.getCode());
          //System.out.print(st.getName() + " " + st.getCode() + " " + ((st.getPrevious() == null)? "\n" : (al.getStationByCode(st.getPrevious()).getName() + st.getPrevious() + " " + al.getDistance(st.getPrevious(), st.getCode()) + "\n")));
          if (st.getName().equals(start)) break;
          time += al.getDistance(st.getPrevious(), st.getCode());
          st = al.getStationByCode(st.getPrevious());
        }
        //System.out.println(time);
        //System.out.println(mintime);
        if (time < mintime) {
          mintime = time;
          beststack = new Stack<String>();
          beststack.addAll(stack);
        }
      }
      while(!beststack.isEmpty()) {
        String s = al.getStationByCode(beststack.pop()).getName();
        if (!beststack.isEmpty() && s.equals(al.getStationByCode(beststack.peek()).getName())){
          if (!first) {
            while(!beststack.isEmpty() && s.equals(al.getStationByCode(beststack.peek()).getName())) {
              beststack.pop();
              mintime -=5;
            }
            if (!beststack.isEmpty()) {
              s = "[" + s + "]";
              mintime += 5;
            }
          }
          else {
            while(!beststack.isEmpty() && s.equals(al.getStationByCode(beststack.peek()).getName())) {
              beststack.pop();
              mintime -= 5;
            } 
            first = false;
          }
        }
        bestresult += s;
        if (!beststack.isEmpty()) bestresult += " ";
        first = false;
      }
      System.out.println(bestresult + "\n" + mintime);
    }

    public static Station getMin(HashSet<Station> unvisited) {
      Station min = null;
      for (Station st : unvisited) {
        if (min == null) min = st;
        else if (min.getDistance() > st.getDistance())
          min = st;
      }
      return min;
    }

    public static void findLeastTransferPath(String start, String end) {
      

    }
    // return > 0 when a > b, < 0 when a < b, 0 when a = b
    public static int compareDistance(int a, int b) {
      if (a == INF) {
        if (b == INF) return 0;
        else return 1;
      }
      else {
        if (b == INF) return -1;
        else return a-b;
      }
    }
    public static int addDistance(int a, int b) {
      if (a == INF || b == INF) return INF;
      else return a + b;
    }
}
