import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Genre, Title 을 관리하는 영화 데이터베이스.
 * 
 * MyLinkedList 를 사용해 각각 Genre와 Title에 따라 내부적으로 정렬된 상태를  
 * 유지하는 데이터베이스이다. 
 */
public class MovieDB {
    MyLinkedList<Genre> genrelist;
    public MovieDB() {
    	// HINT: MovieDBGenre 클래스를 정렬된 상태로 유지하기 위한 
    	// MyLinkedList 타입의 멤버 변수를 초기화 한다.
      
      genrelist = new MyLinkedList<Genre>();

    }

    public void insert(MovieDBItem item) {
        // Insert the given item to the MovieDB.

      String genre = item.getGenre();
      String title = item.getTitle();
      boolean found = false;

      for (int i=0;i<genrelist.getNumItems(); i++){
        if (genrelist.get(i).getItem().compareTo(genre) == 0) {
          for (int j=0; j<genrelist.get(i).movielist.getNumItems(); j++) {
            if (genrelist.get(i).movielist.get(j).compareTo(title) == 0) {
              return;
            }
            else if (genrelist.get(i).movielist.get(j).compareTo(title)>0) {
              genrelist.get(i).movielist.add(title, j);
              return;
            }
          }
          genrelist.get(i).movielist.add(title);
          return;
        }
        else if (genrelist.get(i).getItem().compareTo(genre)>0) {
            Genre g = new Genre(genre);
            g.movielist.add(title);
            genrelist.add(g, i);
            return;
          }
      }
      Genre g = new Genre(genre);
      g.movielist.add(title);
      genrelist.add(g);
      return;
    }

    public void delete(MovieDBItem item) {
        // FIXME implement this
        // Remove the given item from the MovieDB.
        String genre = item.getGenre();
        String title = item.getTitle();
        for (int i=0;i<genrelist.getNumItems();i++) {
          if (genrelist.get(i).getItem().compareTo(genre) == 0) {
            for (int j=0; j<genrelist.get(i).movielist.getNumItems(); j++) {
              if (genrelist.get(i).movielist.get(j).compareTo(title) == 0){
                genrelist.get(i).movielist.remove(j);
                break;
              }
            }
          }
          // if Genre Node is empty, delete the node.
          if (i != genrelist.getNumItems() && genrelist.get(i).movielist.getNumItems() == 0) {
            genrelist.remove(i);
          }
        }
    }

    public MyLinkedList<MovieDBItem> search(String term) {
        // FIXME implement this
        // Search the given term from the MovieDB.
        // You should return a linked list of MovieDBItem.
        // The search command is handled at SearchCmd class.
    	
    	// Printing search results is the responsibility of SearchCmd class. 
    	// So you must not use System.out in this method to achieve specs of the assignment.
    	
    	
    	// FIXME remove this code and return an appropriate MyLinkedList<MovieDBItem> instance.
    	// This code is supplied for avoiding compilation error.   
        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();

        for (int i=0;i<genrelist.getNumItems();i++) {
          for (int j=0;j<genrelist.get(i).movielist.getNumItems();j++){
            if (isSubString(genrelist.get(i).movielist.get(j),term))
            results.add(new MovieDBItem(genrelist.get(i).getItem(), genrelist.get(i).movielist.get(j)));
          }
        }
        return results;
    }

    private boolean isSubString(String str1, String str2) {
      return isSubString(str1, str2, str1.length(), str2.length());
    }

    private boolean isSubString(String str1, String str2, int n1, int n2) {
      if (n2 == 0) return true;
      if (n1 == 0) return false;

      if (str1.charAt(n1-1) == str2.charAt(n2-1)) return isSubString(str1, str2, n1-1, n2-1);
      return isSubString(str1, str2, n1-1, n2);
    }
    
    public MyLinkedList<MovieDBItem> items() {
        // FIXME implement this
        // Search the given term from the MovieDatabase.
        // You should return a linked list of QueryResult.
        // The print command is handled at PrintCmd class.

    	// Printing movie items is the responsibility of PrintCmd class. 
    	// So you must not use System.out in this method to achieve specs of the assignment.


    	// FIXME remove this code and return an appropriate MyLinkedList<MovieDBItem> instance.
        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
        
        for (int i=0;i<genrelist.getNumItems();i++) {
          for (int j=0;j<genrelist.get(i).movielist.getNumItems();j++){
            results.add(new MovieDBItem(genrelist.get(i).getItem(), genrelist.get(i).movielist.get(j)));
          }
        }
    	return results;
    }
}

class Genre extends Node<String> implements Comparable<Genre> {
  MovieList movielist;
	public Genre(String name) {
		super(name);
    movielist = new MovieList();
	}
	
	@Override
	public int compareTo(Genre o) {
    return getItem().compareTo(o.getItem());
	}

	@Override
	public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getItem() == null) ? 0 : getItem().hashCode());

    for (String item : movielist) {
      result = prime * result + (item.hashCode());
    }

    return result;
	}

	@Override
	public boolean equals(Object obj) {
    if (this == obj) 
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Genre other = (Genre) obj;
    if (getItem() == null) {
      if (other.getItem() != null)
        return false;
    } else if (!getItem().equals(other.getItem()))
      return false;
    return true;
	}
}

class MovieList extends MyLinkedList<String> {	
  // insert Node so that the items are sorted in order
  void insertOrder(String s) {
    for (int i=0 ; i<getNumItems()-1 ; i++) {
      if (s.compareTo(get(i)) == 0) return;
      else if (s.compareTo(get(i)) > 0) {
        if (s.compareTo(get(i+1)) == 0) return;
        else if (s.compareTo(get(i+1)) < 0) {
          add(s, i+1);
          return;
        }
      }
    }
    add(s);
    return;
  }

  void deleteOrder(String s) {
    Node<String> prev=head, curr=head.getNext();
    for (int i=0; i<getNumItems(); i++) {
      if (s.compareTo(curr.getItem())==0) {
        prev.setNext(curr.getNext());
      }
      else if (s.compareTo(curr.getItem())>0)
        return;
    }
  }
}
