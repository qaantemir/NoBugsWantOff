package java_express.oop.zoo;

public class Main {
    public static void main(String[] args) {
        Zoo zoo = new Zoo();
        zoo.add(new Elephant());
        zoo.showActions();
    }
}
