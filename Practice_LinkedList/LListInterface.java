public interface LListInterface{
  public boolean isEmpty();
  public int size();
  public void add(int index, Object item) throws IndexOutOfBoundaryException;
  public void remove(int index) throws IndexOutOfBoundaryException;
  public Object get(int index) throws IndexOutOfBoundaryException;
  public void removeAll();
}
