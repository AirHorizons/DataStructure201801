import java.util.ArrayList;
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


    public static void main(String[] args) {
      

      while(true) {
        try {
          BufferedReader br = new BufferedReader(new FileReader(args[0]));
          String input = br.readLine();
          
          if (input.compareTo("QUIT") == 0)
            break;

          command(input);
        }
        catch (IOException e) {
          System.out.println("잘못된 입력입니다. " + e.toString());
        }
      }
    }

    public static void command(String input) {
      if (input.isEmpty()) {
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
          m = qp.matcher(input);
          while (m.find()){
            if (m.group().length() == 4) findLeastTransferPath();
            else
              findShortestPath();
          }
          break;
        default: return;
      }
    }
    /*
        MISC
     */

    // print shortest path. If there is same name within the path, it is transfer station so merge it and wrap it with bracket.
    public static void findShortestPath() {}

    public static void findLeastTransferPath() {}
}
