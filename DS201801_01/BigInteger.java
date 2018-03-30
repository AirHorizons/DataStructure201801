import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
 
public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "입력이 잘못되었습니다.";
 
    // implement this
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile("^([-\\+]?[1-9][0-9]*)([-\\+\\*])([-\\+]?[1-9][0-9]*)$");
    private char[] number;
    private char sign;

    public BigInteger(int i)
    {
      sign = (i>=0)?'+':'-';
      number = new char[digits(i)];
      if (i==0) number[0] = '0';
      for (int index=0;i!=0;i/=10,index++){
        number[index] = Character.forDigit(i%10, 10); // store digits into reverse order 
      }
    }
 
    public BigInteger(int[] num1)
    {
      // sign bit is stored at the first element of the int array, and rest of them are number part.
      int index;
      
      sign = (num1[0] >= 0) ? '+' : '-';
      number = new char[num1.length-1];
      for (index=0;index<number.length;index++)
      {
        number[index] = Character.forDigit(num1[num1.length-1-index], 10);
      }
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
          number[i-1] = s.charAt(s.length()-i);
        }
      }
    }

    private int digits(int i)
    {
      int d;
      if (i==0) return 1;
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
      return new BigInteger(0);
    }

    public BigInteger add_numbers(BigInteger big, char sign)
    {

    }
 
    public BigInteger subtract(BigInteger big)
    {
      return new BigInteger(0);
    }

    public BigInteger subtract_numbers(BigInteger big, char sign)
    {

    }

    private int compare(BigInteger big) // return 1 when this is bigger, -1 when that, 0 when same
    {
      int i;
      if (number.length>big.getNumber().length) return 1;
      else if (number.length<big.getNumber().length) return -1;
      else // length of both number are the same
      {
        for (i=number.length-1;i>=0;i--)
        {
          if (number[i]>big.getNumber()[i]) return 1;
          else if (number[i]<big.getNumber()[i]) return -1;
          else continue;
        }
      }
      return 0; 
    }
 
    public BigInteger multiply(BigInteger big)
    {
      return new BigInteger(0);
    }

    public char multiplySign(BigInteger big)
    {
      if (sign == big.getSign()) return '+';
      else return '-';  
    }
 
    @Override
    public String toString()
    {
      String str = "";
      String number_string = new StringBuffer(new String(number)).reverse().toString();
      String sign_string = Character.toString(sign); 
      str = (sign == '-')? str.concat(sign_string).concat(number_string) : str.concat(number_string);
      return str;
    }
 
    static BigInteger evaluate(String input) throws IllegalArgumentException
    {
        // implement here
        // parse input
        // using regex is allowed
        BigInteger num1 = new BigInteger(0), num2 = new BigInteger(0); //Initiallize by empty value
        char operator = ' '; //Samw Here
        
        // single number = "^[+-]*[1-9][0-9]*$"
	      input = input.replaceAll("\\s+", "");
        Matcher matcher = EXPRESSION_PATTERN.matcher(input);


        while (matcher.find())
        {
	         StringBuilder expr = new StringBuilder(); 
           num1 = new BigInteger(matcher.group(1));
           num2 = new BigInteger(matcher.group(3));
           operator = matcher.group(2).charAt(0);
        }
        
        if (operator == '+') return num1.add(num2);
        else if (operator == '-') return num1.subtract(num2);
        else if (operator == '*') return num1.multiply(num2);
        else throw new IllegalArgumentException();
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
