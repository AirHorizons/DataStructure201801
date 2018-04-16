public interface ListInterface<T> extends Iterable<T> {
	public boolean isEmpty();

	public int size();

	public void add(T item);

	public void add(T item, int i);

	public T first();

	public void removeAll();
}
