interface  Predator {
  String getFood();

  default void printFood() {
    System.out.printf("my food is %s\n", getFood());
  }
}

class Animal {
  String name;

  void setName(String name) {
    this.name = name;
  }
}


class Tiger extends Animal implements Predator {
  public String getFood() {
    return "apple";
  }
}

class Lion extends Animal implements Predator{
  public String getFood() {
    return "banana";
  }
}

class ZooKeeper extends Animal {
  void feed(Predator predator) { // 호랑이가 오면 사과를 던져준다.
    System.out.println("feed " + predator.getFood());
  }
}

public class OOP_Study {
  public static void main(String[] args) {
    ZooKeeper zooKeeper = new ZooKeeper();
    Tiger tiger = new Tiger();
    Lion lion = new Lion();
    zooKeeper.feed(tiger);
    zooKeeper.feed(lion);
  }
}
