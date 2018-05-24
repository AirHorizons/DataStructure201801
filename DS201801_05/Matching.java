import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Matching
{
  private final String input_pattern = "([<@?]) (*)";
  private Pattern p = Pattern.compile(input_pattern);

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
    }
	}
}
