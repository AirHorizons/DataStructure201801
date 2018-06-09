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
    private static int state = 0;


    private static HashSet<Station> unvisited;
    private static HashSet<Station> visited;
    private static HashMap<Station, Integer> distances;
    private static HashMap<Station, Station> previous;
    
    public static void main(String[] args) {
      al = new AdjacencyList(); 
      try {
        BufferedReader br = new BufferedReader(new FileReader(args[0]));

        while(true) {
          String input = br.readLine();
          
          if (input.compareTo("QUIT") == 0)
            break;

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
      for (Station st : al.getMap().values()) unvisited.add(st);
      visited = new HashSet<Station>();
      distances = new HashMap<Station, Integer>();
      previous = new HashMap<Station, Station>();
      Station newstation = al.getStationByName(start);
      newstation.setDistance(0);
      while (!unvisited.isEmpty() && !newstation.getName().equals(end)) {
        newstation = getMin(unvisited);  
        unvisited.remove(newstation);      
        visited.add(newstation);
        for (Station updatestation : unvisited) {
          if (compareDistance(updatestation.getDistance(), addDistance(newstation.getDistance(), al.getDistance(newstation.getCode(), updatestation.getCode()))) > 0) {
            updatestation.setDistance(addDistance(newstation.getDistance(), al.getDistance(newstation.getCode(), updatestation.getCode())));
            updatestation.setPrevious(newstation.getCode());
          } 
        }
      }
      /*
      System.out.println("----------");
      for (Station st : al.getMap().values()) {
        System.out.print(st.getName() + " " + st.getCode() + " " + ((st.getPrevious() == null)? "\n" : (al.getStationByCode(st.getPrevious()).getName() + "\n")));
      }
      System.out.println("----------");
      */
      Station st = al.getStationByName(end);
      Stack<String> stack = new Stack<String>();
      String result = "";
      boolean first = true;
      while(true) {
        stack.push(st.getName());
        if (st.getName().equals(start)) break;
        st = al.getStationByCode(st.getPrevious());
      }
      while(!stack.isEmpty()) {
        String s = stack.pop();
        if (!stack.isEmpty() && s.equals(stack.peek())) {
          if (!first) {
            while(s.equals(stack.peek())) stack.pop();
            if (!stack.isEmpty())
              s = "[" + s + "]";
          }
          else {
            while(s.equals(stack.peek())) stack.pop();
            first = false;
          }
        }
        result += s;
        if (!stack.isEmpty()) result += " ";
        first = false;
      }
      System.out.println(result);
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
      unvisited = new HashSet<Station>();
      for (Station st : al.getMap().values()) unvisited.add(st);
      visited = new HashSet<Station>();
      distances = new HashMap<Station, Integer>();
      previous = new HashMap<Station, Station>();
      Station newstation = al.getStationByName(start);
      newstation.setDistance(0);
      while (!unvisited.isEmpty() && !newstation.getName().equals(end)) {
        newstation = getMin(unvisited);  
        unvisited.remove(newstation);      
        visited.add(newstation);
        for (Station updatestation : unvisited) {
          if (compareDistance(updatestation.getDistance(), addDistance(newstation.getDistance(), al.getDistance(newstation.getCode(), updatestation.getCode()))) > 0) {
            updatestation.setDistance(addDistance(newstation.getDistance(), al.getDistance(newstation.getCode(), updatestation.getCode())));
            updatestation.setPrevious(newstation.getCode());
          } 
        }
      }
      /*
      System.out.println("----------");
      for (Station st : al.getMap().values()) {
        System.out.print(st.getName() + " " + st.getCode() + " " + ((st.getPrevious() == null)? "\n" : (al.getStationByCode(st.getPrevious()).getName() + "\n")));
      }
      System.out.println("----------");
      */
      Station st = al.getStationByName(end);
      Stack<String> stack = new Stack<String>();
      String result = "";
      boolean first = true;
      while(true) {
        stack.push(st.getName());
        if (st.getName().equals(start)) break;
        st = al.getStationByCode(st.getPrevious());
      }
      while(!stack.isEmpty()) {
        String s = stack.pop();
        if (!stack.isEmpty() && s.equals(stack.peek())) {
          if (!first) {
            while(s.equals(stack.peek())) stack.pop();
            if (!stack.isEmpty())
              s = "[" + s + "]";
          }
          else {
            while(s.equals(stack.peek())) stack.pop();
            first = false;
          }
        }
        result += s;
        if (!stack.isEmpty()) result += " ";
        first = false;
      }
      System.out.println(result);

    }
    // return > 0 when a > b, < 0 when a < b, 0 when a = b
    public static int compareDistance(int a, int b) {
      if (a == Integer.MAX_VALUE) return 1;
      else {
        if (b == Integer.MAX_VALUE) return -1;
        else return a-b;
      }
    }
    public static int addDistance(int a, int b) {
      if (a == Integer.MAX_VALUE || b == Integer.MAX_VALUE) return Integer.MAX_VALUE;
      else return a + b;
    }
}
