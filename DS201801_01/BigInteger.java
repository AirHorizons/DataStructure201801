import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
 
public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "입력이 잘못되었습니다.";
 
    // implement this
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile("^([+-]?[1-9][0-9]*)([+-\\.\\*])([+-]?[1-9][0-9]*)$");
    private char[] number;
    private char sign;

    public BigInteger(int i)
    {
      sign = (i>0)?'+':'-';
      number = new char[digits(i)];
      for (int index=0;i!=0;i/=10,index++){
        number[index] = Character.forDigit(i, 10); // store digits into reverse order 
      }
    }
 
    public BigInteger(int[] num1)
    {
      // what for?
    }
 
    public BigInteger(String s)
    {
      int i; // var for iteration

      if (s.charAt(0)=='-') sign = '-';
      else sign = '+';

      if (Character.isDigit(s.charAt(0)))
      {
        number = new char[s.length()];
        for (i=0;i<s.length();i++)
        {
          number[i] = s.charAt(s.length()-1-i);
        }
      }
      else
      {
        number = new char[s.length()-1];
        for (i=1;i<s.length();i++)
        {
          number[i] = s.charAt(s.length()-i);
        }
      }
    }

    private int digits(int i)
    {
      int d;
      for (d=0; i!=0; i/=10)
      {
        d = d+1;
      }
      return d;
    }

    public char[] getNumber()
    {
      return number;
    }

    public char getSign()
    {
      return sign;
    }
 
    public BigInteger add(BigInteger big)
    {
    }
 
    public BigInteger subtract(BigInteger big)
    {
    }
 
    public BigInteger multiply(BigInteger big)
    {
    }
 
    @Override
    public String toString()
    {
      return "";
    }
 
    static BigInteger evaluate(String input) throws IllegalArgumentException
    {
        // implement here
        // parse input
        // using regex is allowed
        
        // single number = "^[+-]*[1-9][0-9]*$"
	      input.replaceAll("\\s+", "");
        Matcher matcher = EXPRESSION_PATTERN.matcher(input);

	      StringBuilder expr = new StringBuilder(); 
        BigInteger num1 = new BigInteger(matcher.group(1));
        BigInteger num2 = new BigInteger(matcher.group(3));
        char operator = matcher.group(2).charAt(0);
        
        // One possible implementation
        // BigInteger num1 = new BigInteger(arg1);
        // BigInteger num2 = new BigInteger(arg2);
        // BigInteger result = num1.add(num2);
        // return result;
    }
 
    public static void main(String[] args) throws Exception
    {
        try (InputStreamReader isr = new InputStreamReader(System.in))
        {
            try (BufferedReader reader = new BufferedReader(isr))
            {
                boolean done = false;
                while (!done)
                {
                    String input = reader.readLine();
 
                    try
                    {
                        done = processInput(input);
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.err.println(MSG_INVALID_INPUT);
                    }
                }
            }
        }
    }
 
    static boolean processInput(String input) throws IllegalArgumentException
    {
        boolean quit = isQuitCmd(input);
 
        if (quit)
        {
            return true;
        }
        else
        {
            BigInteger result = evaluate(input);
            System.out.println(result.toString());
 
            return false;
        }
    }
 
    static boolean isQuitCmd(String input)
    {
        return input.equalsIgnoreCase(QUIT_COMMAND);
    }
}
