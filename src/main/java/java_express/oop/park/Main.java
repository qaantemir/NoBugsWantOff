package java_express.oop.park;

public class Main {
    public static void main(String[] args) {
        Park park = new Park();

        park.add(new Carousel());

        park.howToCare();
    }
}
