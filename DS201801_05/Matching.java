import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Matching
{
  private final static String input_pattern = "([<@?]) (.*)";
  private static Pattern p = Pattern.compile(input_pattern);

  private HashTable<String, StringPosition> ht;


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
     
      ht = new HashTable();

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

      String line = br.readLine();
      int linenum = 1;
      while (line != null) {
        int length = line.length();

        for (int i=0; i<length-5; i++) {
          String token = line.substring(i, i+6);
          ht.get(ht.getHash(token)).insert(token, new StringPosition(linenum, i+1));
        }

        linenum++;
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
    int index = Integer.ParseInt(target);
    if (ht == null) return;
    
    ht.get(index).preorder();
  }
  private static void printPattern(String target) {
  }
  private static LinkedList<StringPosition> printPattern_R(String target, int index, LinkedList<StringPosition> tokens) {
    LinkedList<StringPosition> positions = ht.get(ht.getHash(target.substring(index, index+6))).retrieve(target.substring(index, index+6));
    // tokens와 positions 사이에서 index만큼 차이나는 것들만 filter해서 recursive하게 


    return printPattern_R(target, index+1, tokens);
  }
  private static void printNotFound() { System.out.println("(0, 0)"); }

}
