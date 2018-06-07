class TestAdjacencyList {
  public static void main(String[] args){
     AdjacencyList al = new AdjacencyList();
     Station st1 = new Station("100", "종로3가", "1");
     Station st2 = new Station("300", "종로3가", "3");
     Station st3 = new Station("500", "종로3가", "5");
     Station st4 = new Station("201", "대림", "2");
     al.addStation(st1);
     al.addStation(st2);
     al.addStation(st3);
     System.out.println(al);
  }
}
