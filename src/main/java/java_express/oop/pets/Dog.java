package java_express.oop.pets;

public class Dog extends Pet {
    @Override
    public void doAction() {
        System.out.println("Собака гуляет");
    }

    @Override
    public void feed() {
        System.out.println("Собака ест сухой корм");
    }
}
