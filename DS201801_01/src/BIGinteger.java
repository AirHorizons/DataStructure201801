public class BIGinteger {

    private char[] operand1;
    private char sign1;
    private char[] operand2;
    private char sign2;
    private char operator;

    private char[] add_or_subtract(char[] operand1, char[] operand2, char sign1, char sign2, char operator){



        if (operator == '+' && (sign1 == sign2))
        {
            return add(operand1, operand2, sign1);
        }
        else if ((operator == '-' && (sign1 != sign2)))
        {
            // this  a-(+b) -> a+(-b), when a and b have different sign value
            return add(operand1, operand2, sign1);
        }
        else
        {
            if (compare(operand1, operand2)>0) // |operand1|>|operand2|
            {
                return subtract(operand1, operand2, sign1);
            }
            else if (compare(operand1, operand2)<0) // |operand1|<|operand2|
            {
                return subtract(operand2, operand1, sign2);
            }
            else // when two numbers are exactly same and have opposite sign, return zero.
            {
                char[] zero = new char[1];
                zero[0] = '0';
                return zero;
            }
        }
    }
    private int compare(char[] operand1, char[] operand2){
        //TODO
        return 0;
    }
    private char[] add(char[] operand1, char[] operand2, char sign){
        //TODO
        return new char[0];
    }
    private char[] subtract(char[] operand1, char[] operand2, char sign){
        //TODO
        return new char[0];
    }
    private char[] multiply(char[] operand1, char[] operand2, char sign1, char sign2){
        //TODO
        // decide sign value
        // calculate operands
        return new char[0];
    }


}
