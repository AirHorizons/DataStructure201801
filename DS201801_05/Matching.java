import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Matching
{
  private final static String input_pattern = "([<@?]) (.*)";
  private static Pattern p = Pattern.compile(input_pattern);

  private static HashTable<String, StringPosition> ht;

	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("QUIT") == 0)
					break;

				command(input);
			}
			catch (IOException e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}

	private static void command(String input)
	{
    Matcher m = p.matcher(input);
    while (m.find()) {
      String command = m.group(1);
      String target = m.group(2); 
     

      if (command.equals("<")) {
        readFile(target);
      }
      else if (command.equals("@")) {
        printHash(target);
      }
      else if (command.equals("?")) {
        printPattern(target);
      }
    }
	}
  
  private static void readFile(String target) {
    FileInputStream fis = null;
    BufferedReader br = null;
    try {
      fis = new FileInputStream(target);
      br = new BufferedReader(new InputStreamReader(fis));
      ht = new HashTable<String, StringPosition>();

      String line = br.readLine();
      int linenum = 1;
      while (line != null) {
        int length = line.length();

        for (int i=0; i<length-5; i++) {
          String token = line.substring(i, i+6);
          ht.insert(token, new StringPosition(linenum, i+1));
        }

        linenum++;
        line = br.readLine();
      }
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    finally {
      try {
        br.close();
        fis.close();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  } 
  
  private static void printHash(String target) {
    int index = Integer.parseInt(target);
    if (ht == null) {
      System.out.println("Table is empty");
      return;
    }
    
    ht.retrieveByHash(index).preorderByKey();
  }
  
  private static void printPattern(String target) {
    LinkedList<StringPosition> result = printPattern_R(target, ht.retrieve(target.substring(0, 6)), 0);
    if (result == null || result.isEmpty()) printNotFound();
    else {
      result.print();
    }
  }
  
  private static LinkedList<StringPosition> printPattern_R(String target, LinkedList<StringPosition> tokens, int index) {
    if (tokens == null) return null;
    if (tokens.isEmpty()) return tokens;
    LinkedList<StringPosition> filter = ht.retrieve(target.substring(index, index+6));

    tokens = Filter(tokens, filter, index);
    int nextstep = (target.length()-index >= 12) ? 6 : (target.length()-1 - (index+5));

    if (index >= target.length()-6) return tokens;
    else return printPattern_R(target, tokens, index + nextstep);
  }
  private static void printNotFound() { System.out.println("(0, 0)"); }

  private static LinkedList<StringPosition> Filter(LinkedList<StringPosition> x, LinkedList<StringPosition>filter, int index) {
    LinkedList<StringPosition> filtered = new LinkedList<StringPosition>();
    try {
      int i=0, j=0;
      while (i < x.size() && j < filter.size()) {
        if (x.get(i).compareWithOffset(filter.get(j), index) < 0) i++;
        else if (x.get(i).compareWithOffset(filter.get(j), index) > 0) j++;
        else {
          filtered.add(x.get(i));
          i++;
          j++;
        }
      }
    }
    catch (IndexOutOfBoundaryException e) {
      e.printStackTrace();
    }
    
    return filtered; 
  }
}
