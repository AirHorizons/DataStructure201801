import java.io.*;
import java.util.ArrayList;
import java.util.Stack;
public class CalculatorTest
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("q") == 0)
					break;

				command(input);
			}
			catch (Exception e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}

	private static void command(String input)
	{
		ArrayList<Object> lexer;
		Stack<String> postfix;
		Stack<String> reversedPostfix;
		String printedPostfix;
		long result;
		TokenBuffer tb = new TokenBuffer(input);
		try {
		    lexer = createLexer(tb);
		    //printLexer(lexer);
            postfix = toPostfix(lexer);
            printedPostfix = printPostfix(postfix);
            reversedPostfix = reverseStack(postfix);
            result = calculatePostfix(reversedPostfix);
            System.out.println(printedPostfix);
            System.out.println(result);
        }
        catch (Exception e) {
		    System.out.println("ERROR");
		    //System.out.println(e.getMessage());
        }
	}


	private static ArrayList<Object> createLexer(TokenBuffer tb) throws Exception
    {
        try {
            ArrayList<Object> lexer = new ArrayList<>();

            while(!tb.isEnd()){
                if (tb.getChar() == '(') {
                    // throw error when previous element is invalid
                    if (!lexer.isEmpty() && isNumeric(lexer.get(lexer.size()-1).toString()))
                        throw new Exception("no matching parenthesis for (, pos " + tb.getPos());
                    tb.push();
                    tb.increasePos();
                    lexer.add(createLexer(tb));
                }
                else if (tb.getChar() == ')') {
                    // stack of the buffer should not be empty
                    if (tb.isEmpty()) throw new Exception("no matching parenthesis for )");

                    tb.increasePos();
                    tb.pop();
                    return lexer;
                }
                else if (tb.getChar() == '^') {
                    // invalid input when another operator or nothing precedes this operator
                    if (lexer.isEmpty() || (!(lexer.get(lexer.size()-1) instanceof ArrayList) && isOperator(lexer.get(lexer.size()-1).toString())))
                        throw new Exception("^ operator right after another operator");

                    tb.increasePos();
                    lexer.add("^");
                }
                else if (tb.getChar() == '*') {
                    // invalid input when another operator or nothing precedes this operator
                    if (lexer.isEmpty() || (!(lexer.get(lexer.size()-1) instanceof ArrayList) && isOperator(lexer.get(lexer.size()-1).toString())))
                        throw new Exception("* operator right after another operator");

                    tb.increasePos();
                    lexer.add("*");
                }
                else if (tb.getChar() == '/') {
                    // invalid input when another operator or nothing precedes this operator
                    if (lexer.isEmpty() || (!(lexer.get(lexer.size()-1) instanceof ArrayList) && isOperator(lexer.get(lexer.size()-1).toString())))
                        throw new Exception("/ operator right after another operator");

                    tb.increasePos();
                    lexer.add("/");
                }
                else if (tb.getChar() == '%') {
                    // invalid input when another operator or nothing precedes this operator
                    if (lexer.isEmpty() || (!(lexer.get(lexer.size()-1) instanceof ArrayList) && isOperator(lexer.get(lexer.size()-1).toString())))
                        throw new Exception("% operator right after another operator");

                    tb.increasePos();
                    lexer.add("%");
                }
                else if (tb.getChar() == '+') {
                    // invalid input when another operator or nothing precedes this operator
                    if (lexer.isEmpty() || (!(lexer.get(lexer.size()-1) instanceof ArrayList) && isOperator(lexer.get(lexer.size()-1).toString())))
                        throw new Exception("+ operator right after another operator");

                    tb.increasePos();
                    lexer.add("+");
                }
                else if (tb.getChar() == '-') {
                    // invalid input when another operator or nothing precedes this operator
                    if (lexer.isEmpty() || (!(lexer.get(lexer.size()-1) instanceof ArrayList) && isOperator(lexer.get(lexer.size()-1).toString()))) {
                        tb.increasePos();
                        lexer.add("~");
                        continue;
                    }

                    tb.increasePos();
                    lexer.add("-");
                }
                else if (Character.isDigit(tb.getChar())){
                    // if there is another number in front of number, throw exception
                    if (!lexer.isEmpty() && isNumeric(lexer.get(lexer.size()-1).toString()))
                        throw new Exception("Got number right after number");
                    // if there is parenthesis in front of number, throw exception
                    if (!lexer.isEmpty() && lexer.get(lexer.size()-1) instanceof ArrayList)
                        throw new Exception("Got number right after parenthesis");

                    lexer.add(insertNumber(tb));

                    // insert complete integer into the lexer
                }
                else if (tb.getChar() == ' ') {
                    tb.increasePos();
                }
                else throw new Exception("Invalid Input");
            }

            if (!tb.isEmpty()) throw new Exception("Stack is not empty after iteration");

            return lexer;
        }
        catch (Exception e) {
            throw e;
        }
    }

    private static void printLexer(ArrayList<Object> lexer) {
	    for (int i=0;i<lexer.size();i++)
	        System.out.print(lexer.get(i) + " ");
	    System.out.println();
    }

    private static boolean isNumeric(String s) {
	    for (int i=0;i<s.length();i++) {
	        if (!Character.isDigit(s.charAt(i))) return false;
        }
	    return true;
    }

    private static boolean isOperator(String s) {
	    return
                s.compareTo("^")==0 ||
                s.compareTo("~")==0 ||
                s.compareTo("*")==0 ||
                s.compareTo("/")==0 ||
                s.compareTo("%")==0 ||
                s.compareTo("+")==0 ||
                s.compareTo("-")==0 ||
                s.compareTo("~")==0;
    }

    private static String insertNumber(TokenBuffer tb) {
	    String numString = "";

	    do { // first character is always digit
	        numString += tb.getChar();
	        tb.increasePos();
        } while(!tb.isEnd() && Character.isDigit(tb.getChar()));

	    return numString;
    }

    private static Stack<String> toPostfix(ArrayList<Object> lexer) throws Exception {
	    Stack<String> postfix = new Stack<>();
        Stack<String> tempStack = new Stack<>();
        try {
            for (int i = 0; i < lexer.size(); i++) {
                if (lexer.get(i) instanceof ArrayList)
                    postfix.addAll(toPostfix((ArrayList<Object>)lexer.get(i)));
                else if (isNumeric((String) lexer.get(i))) {
                    postfix.push((String) lexer.get(i));
                }
                else if (isOperator((String) lexer.get(i))) {
                    while (!tempStack.isEmpty() &&
                            (getPriority(tempStack.peek()) <= getPriority((String) lexer.get(i))) &&
                            !(((getPriority(tempStack.peek()) == getPriority((String) lexer.get(i))) && (((String)lexer.get(i)).equals("~") || ((String)lexer.get(i)).equals("^"))))){
                        postfix.push(tempStack.pop());
                    }
                    tempStack.push((String)lexer.get(i));
                }
            }
            while(!tempStack.isEmpty())
                postfix.push(tempStack.pop());
        }
        catch (Exception e) { throw e; }

	    return postfix;
    }

    private static String printPostfix(Stack<String> postfix) {
	    String result = "";
	    for (int i=0; i<postfix.size(); i++){
	        result += postfix.get(i);
	        if (i != postfix.size()-1) result += " ";
        }

	    return result;
    }

    private static Stack<String> reverseStack(Stack<String> stack) {
	    Stack<String> result = new Stack<>();
	    while (!stack.isEmpty())
            result.push(stack.pop());
	    return result;
    }

    private static long calculatePostfix(Stack<String> postfix) throws Exception {
	    Stack<Long> longStack = new Stack<>();
	    long operand1, operand2;
        String element;
	    try {
            while (!postfix.isEmpty()) {
                element = postfix.pop();

                if (isNumeric(element))
                    longStack.push(Long.parseLong(element));
                else if (element.equals("~")) {
                    if (longStack.isEmpty()) throw new Exception("No operand for operator ~");
                    else {
                        longStack.push(-1*longStack.pop());
                    }
                }
                else if (element.equals("+")) {
                    if (longStack.isEmpty()) throw new Exception("No operand for operator +");
                    operand1 = longStack.pop();
                    if (longStack.isEmpty()) throw new Exception("No operand for operator +");
                    operand2 = longStack.pop();
                    longStack.push(operand1 + operand2);
                }
                else if (element.equals("-")) {
                    if (longStack.isEmpty()) throw new Exception("No operand for operator -");
                    operand1 = longStack.pop();
                    if (longStack.isEmpty()) throw new Exception("No operand for operator -");
                    operand2 = longStack.pop();
                    longStack.push(operand2 - operand1);
                }
                else if (element.equals("*")) {
                    if (longStack.isEmpty()) throw new Exception("No operand for operator *");
                    operand1 = longStack.pop();
                    if (longStack.isEmpty()) throw new Exception("No operand for operator *");
                    operand2 = longStack.pop();
                    longStack.push(operand1 * operand2);
                }
                else if (element.equals("/")) {
                    if (longStack.isEmpty()) throw new Exception("No operand for operator /");
                    operand1 = longStack.pop();
                    if (longStack.isEmpty()) throw new Exception("No operand for operator /");
                    operand2 = longStack.pop();
                    if (operand1 == 0) throw new Exception("Can't be divided by zero");
                    longStack.push(operand2 / operand1);
                }
                else if (element.equals("%")) {
                    if (longStack.isEmpty()) throw new Exception("No operand for operator %");
                    operand1 = longStack.pop();
                    if (longStack.isEmpty()) throw new Exception("No operand for operator %");
                    operand2 = longStack.pop();
                    if (operand1 == 0) throw new Exception("Can't be divided by zero");
                    longStack.push(operand2 % operand1);
                }
                else if (element.equals("^")) {
                    if (longStack.isEmpty()) throw new Exception("No operand for operator ^");
                    operand1 = longStack.pop();
                    if (longStack.isEmpty()) throw new Exception("No operand for operator ^");
                    operand2 = longStack.pop();
                    if (operand2 == 0 && operand1 < 0) throw new Exception("Can't be divided by zero");
                    longStack.push(Double.valueOf(Math.pow(Long.valueOf(operand2).doubleValue(), Long.valueOf(operand1).doubleValue())).longValue());
                }
                else throw new Exception("Invalid input");
            }
            if (longStack.size() != 1) throw new Exception("Not enough operators");
        }
        catch (Exception e) { throw e; }
        return longStack.pop();
    }

    private static int getPriority(String op) throws Exception{
	    try {
            if (op.equals("^")) return 0;
            else if (op.equals("~")) return 1;
            else if (op.equals("*") || op.equals("/") || op.equals("%")) return 2;
            else if (op.equals("+") || op.equals("-")) return 3;
            else throw new Exception("getPriority function got invalid operator input");
        }
        catch (Exception e) { throw e; }
    }
}
