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
      while (line != null) {
        int length = line.length();
        // TODO : take 6 characters and insert AVL trees
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
    // TODO
  }
  

}
