class TestAdjacencyList {
  public static void main(String[] args){
     AdjacencyList al = new AdjacencyList();
     Station st1 = new Station("100", "신도림", "1");
     Station st2 = new Station("200", "신도림", "2");
     Station st3 = new Station("201", "대림", "2");
     al.addStation(st1);
     al.addStation(st2);
     al.addStation(st3);
  }
}
