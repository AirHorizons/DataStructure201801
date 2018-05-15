public class TokenBuffer {
	    private String s;
	    private int pos, maxPos, top;

	    public TokenBuffer(String s) {
	        this.s = s;
	        this.pos = 0;
	        this.maxPos = s.length()-1;
	        this.top = 0;
        }

        // lexing ends when the string meets an end
        public boolean isEnd() { return pos > maxPos; }
        public void increasePos() { pos++; }

        // Stack related functions
        public boolean isEmpty() { return top==0; }
        public void push() { top++; }
        public int pop() { return top--; }

        public char getChar() throws IndexOutOfBoundsException {
	    	if (pos > maxPos || pos < 0) throw new IndexOutOfBoundsException("Out of index");
	    	else return s.charAt(pos);
	    }

	    public int getPos() { return pos; }
}
